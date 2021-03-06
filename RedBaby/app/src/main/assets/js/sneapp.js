//snapp.js
(function () {
    var SNAPP_VERSION_LABEL = '1.0.0';

    var require, define;

    (function () {
        var modules = {},
        // Stack of moduleIds currently being built.
            requireStack = [],
        // Map of module ID -> index into requireStack of modules currently being built.
            inProgressModules = {},
            SEPARATOR = ".";

        function build(module) {
            var factory = module.factory,
                localRequire = function (id) {
                    var resultantId = id;
                    //Its a relative path, so lop off the last portion and add the id (minus "./")
                    if (id.charAt(0) === ".") {
                        resultantId = module.id.slice(0, module.id.lastIndexOf(SEPARATOR)) + SEPARATOR + id.slice(2);
                    }
                    return require(resultantId);
                };
            module.exports = {};
            delete module.factory;
            factory(localRequire, module.exports, module);
            return module.exports;
        }

        require = function (id) {
            if (!modules[id]) {
                throw "module " + id + " not found";
            } else if (id in inProgressModules) {
                var cycle = requireStack.slice(inProgressModules[id]).join('->') + '->' + id;
                throw "Cycle in require graph: " + cycle;
            }
            if (modules[id].factory) {
                try {
                    inProgressModules[id] = requireStack.length;
                    requireStack.push(id);
                    return build(modules[id]);
                } finally {
                    delete inProgressModules[id];
                    requireStack.pop();
                }
            }
            return modules[id].exports;
        };

        define = function (id, factory) {
            if (modules[id]) {
                throw "module " + id + " already defined";
            }

            modules[id] = {
                id: id,
                factory: factory
            };
        };

        define.remove = function (id) {
            delete modules[id];
        };

        define.moduleMap = modules;
    })();

    //Export for use in node
    if (typeof module === "object" && typeof require === "function") {
        module.exports.require = require;
        module.exports.define = define;
    }

    // file: src/sneapp.js
    define("snapp",
        function (require, exports, module) {

            var channel = require('snapp/channel');
            var platform = require('snapp/platform');

            /**
             * Intercept calls to addEventListener + removeEventListener and handle deviceready,
             * resume, and pause events.
             */
            var m_document_addEventListener = document.addEventListener;
            var m_document_removeEventListener = document.removeEventListener;
            var m_window_addEventListener = window.addEventListener;
            var m_window_removeEventListener = window.removeEventListener;

            /**
             * Houses custom event handlers to intercept on document + window event listeners.
             */
            var documentEventHandlers = {},
                windowEventHandlers = {};

            document.addEventListener = function (evt, handler, capture) {
                var e = evt.toLowerCase();
                if (typeof documentEventHandlers[e] != 'undefined') {
                    documentEventHandlers[e].subscribe(handler);
                } else {
                    m_document_addEventListener.call(document, evt, handler, capture);
                }
            };

            window.addEventListener = function (evt, handler, capture) {
                var e = evt.toLowerCase();
                if (typeof windowEventHandlers[e] != 'undefined') {
                    windowEventHandlers[e].subscribe(handler);
                } else {
                    m_window_addEventListener.call(window, evt, handler, capture);
                }
            };

            document.removeEventListener = function (evt, handler, capture) {
                var e = evt.toLowerCase();
                // If unsubscribing from an event that is handled by a plugin
                if (typeof documentEventHandlers[e] != "undefined") {
                    documentEventHandlers[e].unsubscribe(handler);
                } else {
                    m_document_removeEventListener.call(document, evt, handler, capture);
                }
            };

            window.removeEventListener = function (evt, handler, capture) {
                var e = evt.toLowerCase();
                // If unsubscribing from an event that is handled by a plugin
                if (typeof windowEventHandlers[e] != "undefined") {
                    windowEventHandlers[e].unsubscribe(handler);
                } else {
                    m_window_removeEventListener.call(window, evt, handler, capture);
                }
            };

            function createEvent(type, data) {
                var event = document.createEvent('Events');
                event.initEvent(type, false, false);
                if (data) {
                    for (var i in data) {
                        if (data.hasOwnProperty(i)) {
                            event[i] = data[i];
                        }
                    }
                }
                return event;
            }

            var snapp = {
                define: define,
                require: require,
                version: SNAPP_VERSION_LABEL,
                platformVersion: SNAPP_VERSION_LABEL,
                platformId: platform.id,
                /**
                 * Methods to add/remove your own addEventListener hijacking on document + window.
                 */
                addWindowEventHandler: function (event) {
                    return (windowEventHandlers[event] = channel.create(event));
                },
                addStickyDocumentEventHandler: function (event) {
                    return (documentEventHandlers[event] = channel.createSticky(event));
                },
                addDocumentEventHandler: function (event) {
                    return (documentEventHandlers[event] = channel.create(event));
                },
                removeWindowEventHandler: function (event) {
                    delete windowEventHandlers[event];
                },
                removeDocumentEventHandler: function (event) {
                    delete documentEventHandlers[event];
                },
                /**
                 * Retrieve original event handlers that were replaced by Snapp
                 *
                 * @return object
                 */
                getOriginalHandlers: function () {
                    return {
                        'document': {
                            'addEventListener': m_document_addEventListener,
                            'removeEventListener': m_document_removeEventListener
                        },
                        'window': {
                            'addEventListener': m_window_addEventListener,
                            'removeEventListener': m_window_removeEventListener
                        }
                    };
                },
                /**
                 * Method to fire event from native code
                 * bNoDetach is required for events which cause an exception which needs to be caught in native code
                 */
                fireDocumentEvent: function (type, data, bNoDetach) {
                    var evt = createEvent(type, data);
                    if (typeof documentEventHandlers[type] != 'undefined') {
                        if (bNoDetach) {
                            documentEventHandlers[type].fire(evt);
                        } else {
                            setTimeout(function () {
                                    // Fire deviceready on listeners that were registered before sneapp.js was loaded.
                                    if (type == 'deviceready') {
                                        document.dispatchEvent(evt);
                                    }
                                    documentEventHandlers[type].fire(evt);
                                },
                                0);
                        }
                    } else {
                        document.dispatchEvent(evt);
                    }
                },
                fireWindowEvent: function (type, data) {
                    var evt = createEvent(type, data);
                    if (typeof windowEventHandlers[type] != 'undefined') {
                        setTimeout(function () {
                                windowEventHandlers[type].fire(evt);
                            },
                            0);
                    } else {
                        window.dispatchEvent(evt);
                    }
                },

                /**
                 * Plugin callback mechanism.
                 */
                // Randomize the starting callbackId to avoid collisions after refreshing or navigating.
                // This way, it's very unlikely that any new callback would get the same callbackId as an old callback.
                callbackId: Math.floor(Math.random() * 2000000000),
                callbacks: {},
                callbackStatus: {
                    NO_RESULT: 0,
                    OK: 1,
                    CLASS_NOT_FOUND_EXCEPTION: 2,
                    ILLEGAL_ACCESS_EXCEPTION: 3,
                    INSTANTIATION_EXCEPTION: 4,
                    MALFORMED_URL_EXCEPTION: 5,
                    IO_EXCEPTION: 6,
                    INVALID_ACTION: 7,
                    JSON_EXCEPTION: 8,
                    ERROR: 9
                },

                /**
                 * Called by native code when returning successful result from an action.
                 */
                callbackSuccess: function (callbackId, args) {
                    try {
                        snapp.callbackFromNative(callbackId, true, args.status, [args.message], args.keepCallback);
                    } catch (e) {
                        console.log("Error in success callback: " + callbackId + " = " + e);
                    }
                },

                /**
                 * Called by native code when returning error result from an action.
                 */
                callbackError: function (callbackId, args) {
                    // TODO: Deprecate callbackSuccess and callbackError in favour of callbackFromNative.
                    // Derive success from status.
                    try {
                        snapp.callbackFromNative(callbackId, false, args.status, [args.message], args.keepCallback);
                    } catch (e) {
                        console.log("Error in error callback: " + callbackId + " = " + e);
                    }
                },

                /**
                 * Called by native code when returning the result from an action.
                 */
                callbackFromNative: function (callbackId, success, status, args, keepCallback) {
                    var callback = snapp.callbacks[callbackId];

                    if (callback) {
                        if (success && status == snapp.callbackStatus.OK) {
                            console.log(args);
                            callback.success && callback.success.apply(null, args);
                        } else if (!success) {
                            callback.fail && callback.fail.apply(null, args);
                        }

                        // Clear callback if not expecting any more results
                        if (!keepCallback) {
                            delete snapp.callbacks[callbackId];
                        }
                    }
                },
                addConstructor: function (func) {
                    channel.onSnappReady.subscribe(function () {
                        try {
                            func();
                        } catch (e) {
                            console.log("Failed to run constructor: " + e);
                        }
                    });
                }
            };

            module.exports = snapp;

        });

    // file: src/android/android/nativeapiprovider.js
    define("snapp/android/nativeapiprovider",
        function (require, exports, module) {

            /**
             * Exports the ExposedJsApi.java object if available, otherwise exports the PromptBasedNativeApi.
             */
            var nativeApi = this._snappNative || require('snapp/android/promptbasednativeapi');
            var currentApi = nativeApi;

            module.exports = {
                get: function () {
                    return currentApi;
                },
                setPreferPrompt: function (value) {
                    currentApi = value ? require('snapp/android/promptbasednativeapi') : nativeApi;
                },
                // Used only by tests.
                set: function (value) {
                    currentApi = value;
                }
            };

        });

    // file: src/android/android/promptbasednativeapi.js
    define("snapp/android/promptbasednativeapi",
        function (require, exports, module) {
            var utils = require('snapp/utils');
            /**
             * Implements the API of ExposedJsApi.java, but uses prompt() to communicate.
             * This is used pre-JellyBean, where addJavascriptInterface() is disabled.
             */
            module.exports = {
                exec: function (bridgeSecret, service, action, callbackId, argsJson) {
                    var p = utils.prompt(argsJson, 'snapp:' + JSON.stringify([bridgeSecret, service, action, callbackId]));
                    console.log(argsJson)
                    console.log(p)
                    return p;
                },
                setNativeToJsBridgeMode: function (bridgeSecret, value) {
                    utils.prompt(value, 'snapp_bridge_mode:' + bridgeSecret);
                },
                retrieveJsMessages: function (bridgeSecret, fromOnlineEvent) {
                    console.log('snapp_poll:' + bridgeSecret)
                    return utils.prompt(+fromOnlineEvent, 'snapp_poll:' + bridgeSecret);
                }
            };

        });

    // file: src/common/argscheck.js
    define("snapp/argscheck",
        function (require, exports, module) {

            var exec = require('snapp/exec');
            var utils = require('snapp/utils');

            var moduleExports = module.exports;

            var typeMap = {
                'A': 'Array',
                'D': 'Date',
                'N': 'Number',
                'S': 'String',
                'F': 'Function',
                'O': 'Object'
            };

            function extractParamName(callee, argIndex) {
                return (/.*?\((.*?)\)/).exec(callee)[1].split(', ')[argIndex];
            }

            function checkArgs(spec, functionName, args, opt_callee) {
                if (!moduleExports.enableChecks) {
                    return;
                }
                var errMsg = null;
                var typeName;
                for (var i = 0; i < spec.length; ++i) {
                    var c = spec.charAt(i),
                        cUpper = c.toUpperCase(),
                        arg = args[i];
                    // Asterix means allow anything.
                    if (c == '*') {
                        continue;
                    }
                    typeName = utils.typeName(arg);
                    if ((arg === null || arg === undefined) && c == cUpper) {
                        continue;
                    }
                    if (typeName != typeMap[cUpper]) {
                        errMsg = 'Expected ' + typeMap[cUpper];
                        break;
                    }
                }
                if (errMsg) {
                    errMsg += ', but got ' + typeName + '.';
                    errMsg = 'Wrong type for parameter "' + extractParamName(opt_callee || args.callee, i) + '" of ' + functionName + ': ' + errMsg;
                    // Don't log when running unit tests.
                    if (typeof jasmine == 'undefined') {
                        console.error(errMsg);
                    }
                    throw TypeError(errMsg);
                }
            }

            function getValue(value, defaultValue) {
                return value === undefined ? defaultValue : value;
            }

            moduleExports.checkArgs = checkArgs;
            moduleExports.getValue = getValue;
            moduleExports.enableChecks = true;

        });

    // file: src/common/base64.js
    define("snapp/base64",
        function (require, exports, module) {

            var base64 = exports;

            base64.fromArrayBuffer = function (arrayBuffer) {
                var array = new Uint8Array(arrayBuffer);
                return uint8ToBase64(array);
            };

            base64.toArrayBuffer = function (str) {
                var decodedStr = typeof atob != 'undefined' ? atob(str) : new Buffer(str, 'base64').toString('binary');
                var arrayBuffer = new ArrayBuffer(decodedStr.length);
                var array = new Uint8Array(arrayBuffer);
                for (var i = 0,
                         len = decodedStr.length; i < len; i++) {
                    array[i] = decodedStr.charCodeAt(i);
                }
                return arrayBuffer;
            };

            //------------------------------------------------------------------------------
            /* This code is based on the performance tests at http://jsperf.com/b64tests
             * This 12-bit-at-a-time algorithm was the best performing version on all
             * platforms tested.
             */

            var b64_6bit = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
            var b64_12bit;

            var b64_12bitTable = function () {
                b64_12bit = [];
                for (var i = 0; i < 64; i++) {
                    for (var j = 0; j < 64; j++) {
                        b64_12bit[i * 64 + j] = b64_6bit[i] + b64_6bit[j];
                    }
                }
                b64_12bitTable = function () {
                    return b64_12bit;
                };
                return b64_12bit;
            };

            function uint8ToBase64(rawData) {
                var numBytes = rawData.byteLength;
                var output = "";
                var segment;
                var table = b64_12bitTable();
                for (var i = 0; i < numBytes - 2; i += 3) {
                    segment = (rawData[i] << 16) + (rawData[i + 1] << 8) + rawData[i + 2];
                    output += table[segment >> 12];
                    output += table[segment & 0xfff];
                }
                if (numBytes - i == 2) {
                    segment = (rawData[i] << 16) + (rawData[i + 1] << 8);
                    output += table[segment >> 12];
                    output += b64_6bit[(segment & 0xfff) >> 6];
                    output += '=';
                } else if (numBytes - i == 1) {
                    segment = (rawData[i] << 16);
                    output += table[segment >> 12];
                    output += '==';
                }
                return output;
            }

        });

    // file: src/common/builder.js
    define("snapp/builder",
        function (require, exports, module) {

            var utils = require('snapp/utils');

            function each(objects, func, context) {
                for (var prop in objects) {
                    if (objects.hasOwnProperty(prop)) {
                        func.apply(context, [objects[prop], prop]);
                    }
                }
            }

            function clobber(obj, key, value) {
                exports.replaceHookForTesting(obj, key);
                obj[key] = value;
                // Getters can only be overridden by getters.
                if (obj[key] !== value) {
                    utils.defineGetter(obj, key,
                        function () {
                            return value;
                        });
                }
            }

            function assignOrWrapInDeprecateGetter(obj, key, value, message) {
                if (message) {
                    utils.defineGetter(obj, key,
                        function () {
                            console.log(message);
                            delete obj[key];
                            clobber(obj, key, value);
                            return value;
                        });
                } else {
                    clobber(obj, key, value);
                }
            }

            function include(parent, objects, clobber, merge) {
                each(objects,
                    function (obj, key) {
                        try {
                            var result = obj.path ? require(obj.path) : {};

                            if (clobber) {
                                // Clobber if it doesn't exist.
                                if (typeof parent[key] === 'undefined') {
                                    assignOrWrapInDeprecateGetter(parent, key, result, obj.deprecated);
                                } else if (typeof obj.path !== 'undefined') {
                                    // If merging, merge properties onto parent, otherwise, clobber.
                                    if (merge) {
                                        recursiveMerge(parent[key], result);
                                    } else {
                                        assignOrWrapInDeprecateGetter(parent, key, result, obj.deprecated);
                                    }
                                }
                                result = parent[key];
                            } else {
                                // Overwrite if not currently defined.
                                if (typeof parent[key] == 'undefined') {
                                    assignOrWrapInDeprecateGetter(parent, key, result, obj.deprecated);
                                } else {
                                    // Set result to what already exists, so we can build children into it if they exist.
                                    result = parent[key];
                                }
                            }

                            if (obj.children) {
                                include(result, obj.children, clobber, merge);
                            }
                        } catch (e) {
                            utils.alert('Exception building Snapp JS globals: ' + e + ' for key "' + key + '"');
                        }
                    });
            }

            /**
             * Merge properties from one object onto another recursively.  Properties from
             * the src object will overwrite existing target property.
             *
             * @param target Object to merge properties into.
             * @param src Object to merge properties from.
             */
            function recursiveMerge(target, src) {
                for (var prop in src) {
                    if (src.hasOwnProperty(prop)) {
                        if (target.prototype && target.prototype.constructor === target) {
                            // If the target object is a constructor override off prototype.
                            clobber(target.prototype, prop, src[prop]);
                        } else {
                            if (typeof src[prop] === 'object' && typeof target[prop] === 'object') {
                                recursiveMerge(target[prop], src[prop]);
                            } else {
                                clobber(target, prop, src[prop]);
                            }
                        }
                    }
                }
            }

            exports.buildIntoButDoNotClobber = function (objects, target) {
                include(target, objects, false, false);
            };
            exports.buildIntoAndClobber = function (objects, target) {
                include(target, objects, true, false);
            };
            exports.buildIntoAndMerge = function (objects, target) {
                include(target, objects, true, true);
            };
            exports.recursiveMerge = recursiveMerge;
            exports.assignOrWrapInDeprecateGetter = assignOrWrapInDeprecateGetter;
            exports.replaceHookForTesting = function () {
            };

        });

    // file: src/common/channel.js
    define("snapp/channel",
        function (require, exports, module) {

            var utils = require('snapp/utils'),
                nextGuid = 1;

            /**
             * Custom pub-sub "channel" that can have functions subscribed to it
             * This object is used to define and control firing of events for
             * snapp initialization, as well as for custom events thereafter.
             *
             * The order of events during page load and Snapp startup is as follows:
             *
             * onDOMContentLoaded*         Internal event that is received when the web page is loaded and parsed.
             * onNativeReady*              Internal event that indicates the Snapp native side is ready.
             * onSnappReady*             Internal event fired when all Snapp JavaScript objects have been created.
             * onDeviceReady*              User event fired to indicate that Snapp is ready
             * onResume                    User event fired to indicate a start/resume lifecycle event
             * onPause                     User event fired to indicate a pause lifecycle event
             * onDestroy*                  Internal event fired when app is being destroyed (User should use window.onunload event, not this one).
             *
             * The events marked with an * are sticky. Once they have fired, they will stay in the fired state.
             * All listeners that subscribe after the event is fired will be executed right away.
             *
             * The only Snapp events that user code should register for are:
             *      deviceready           Snapp native code is initialized and Snapp APIs can be called from JavaScript
             *      pause                 App has moved to background
             *      resume                App has returned to foreground
             *
             * Listeners can be registered as:
             *      document.addEventListener("deviceready", myDeviceReadyListener, false);
             *      document.addEventListener("resume", myResumeListener, false);
             *      document.addEventListener("pause", myPauseListener, false);
             *
             * The DOM lifecycle events should be used for saving and restoring state
             *      window.onload
             *      window.onunload
             *
             */

            /**
             * Channel
             * @constructor
             * @param type  String the channel name
             */
            var Channel = function (type, sticky) {
                    this.type = type;
                    // Map of guid -> function.
                    this.handlers = {};
                    // 0 = Non-sticky, 1 = Sticky non-fired, 2 = Sticky fired.
                    this.state = sticky ? 1 : 0;
                    // Used in sticky mode to remember args passed to fire().
                    this.fireArgs = null;
                    // Used by onHasSubscribersChange to know if there are any listeners.
                    this.numHandlers = 0;
                    // Function that is called when the first listener is subscribed, or when
                    // the last listener is unsubscribed.
                    this.onHasSubscribersChange = null;
                },
                channel = {
                    /**
                     * Calls the provided function only after all of the channels specified
                     * have been fired. All channels must be sticky channels.
                     */
                    join: function (h, c) {
                        var len = c.length,
                            i = len,
                            f = function () {
                                if (!(--i)) h();
                            };
                        for (var j = 0; j < len; j++) {
                            if (c[j].state === 0) {
                                throw Error('Can only use join with sticky channels.');
                            }
                            c[j].subscribe(f);
                        }
                        if (!len) h();
                    },
                    create: function (type) {
                        return channel[type] = new Channel(type, false);
                    },
                    createSticky: function (type) {
                        return channel[type] = new Channel(type, true);
                    },

                    /**
                     * snapp Channels that must fire before "deviceready" is fired.
                     */
                    deviceReadyChannelsArray: [],
                    deviceReadyChannelsMap: {},

                    /**
                     * Indicate that a feature needs to be initialized before it is ready to be used.
                     * This holds up Snapp's "deviceready" event until the feature has been initialized
                     * and Snapp.initComplete(feature) is called.
                     *
                     * @param feature {String}     The unique feature name
                     */
                    waitForInitialization: function (feature) {
                        if (feature) {
                            var c = channel[feature] || this.createSticky(feature);
                            this.deviceReadyChannelsMap[feature] = c;
                            this.deviceReadyChannelsArray.push(c);
                        }
                    },

                    /**
                     * Indicate that initialization code has completed and the feature is ready to be used.
                     *
                     * @param feature {String}     The unique feature name
                     */
                    initializationComplete: function (feature) {
                        var c = this.deviceReadyChannelsMap[feature];
                        if (c) {
                            c.fire();
                        }
                    }
                };

            function forceFunction(f) {
                if (typeof f != 'function') throw "Function required as first argument!";
            }

            /**
             * Subscribes the given function to the channel. Any time that
             * Channel.fire is called so too will the function.
             * Optionally specify an execution context for the function
             * and a guid that can be used to stop subscribing to the channel.
             * Returns the guid.
             */
            Channel.prototype.subscribe = function (f, c) {
                // need a function to call
                forceFunction(f);
                if (this.state == 2) {
                    f.apply(c || this, this.fireArgs);
                    return;
                }

                var func = f,
                    guid = f.observer_guid;
                if (typeof c == "object") {
                    func = utils.close(c, f);
                }

                if (!guid) {
                    // first time any channel has seen this subscriber
                    guid = '' + nextGuid++;
                }
                func.observer_guid = guid;
                f.observer_guid = guid;

                // Don't add the same handler more than once.
                if (!this.handlers[guid]) {
                    this.handlers[guid] = func;
                    this.numHandlers++;
                    if (this.numHandlers == 1) {
                        this.onHasSubscribersChange && this.onHasSubscribersChange();
                    }
                }
            };

            /**
             * Unsubscribes the function with the given guid from the channel.
             */
            Channel.prototype.unsubscribe = function (f) {
                // need a function to unsubscribe
                forceFunction(f);

                var guid = f.observer_guid,
                    handler = this.handlers[guid];
                if (handler) {
                    delete this.handlers[guid];
                    this.numHandlers--;
                    if (this.numHandlers === 0) {
                        this.onHasSubscribersChange && this.onHasSubscribersChange();
                    }
                }
            };

            /**
             * Calls all functions subscribed to this channel.
             */
            Channel.prototype.fire = function (e) {
                var fail = false,
                    fireArgs = Array.prototype.slice.call(arguments);
                // Apply stickiness.
                if (this.state == 1) {
                    this.state = 2;
                    this.fireArgs = fireArgs;
                }
                if (this.numHandlers) {
                    // Copy the values first so that it is safe to modify it from within
                    // callbacks.
                    var toCall = [];
                    for (var item in this.handlers) {
                        toCall.push(this.handlers[item]);
                    }
                    for (var i = 0; i < toCall.length; ++i) {
                        toCall[i].apply(this, fireArgs);
                    }
                    if (this.state == 2 && this.numHandlers) {
                        this.numHandlers = 0;
                        this.handlers = {};
                        this.onHasSubscribersChange && this.onHasSubscribersChange();
                    }
                }
            };

            // defining them here so they are ready super fast!
            // DOM event that is received when the web page is loaded and parsed.
            channel.createSticky('onDOMContentLoaded');

            // Event to indicate the Snapp native side is ready.
            channel.createSticky('onNativeReady');

            // Event to indicate that all Snapp JavaScript objects have been created
            // and it's time to run plugin constructors.
            channel.createSticky('onSnappReady');

            // Event to indicate that all automatically loaded JS plugins are loaded and ready.
            channel.createSticky('onPluginsReady');

            // Event to indicate that Snapp is ready
            channel.createSticky('onDeviceReady');

            // Event to indicate a resume lifecycle event
            channel.create('onResume');

            // Event to indicate a pause lifecycle event
            channel.create('onPause');

            // Event to indicate a destroy lifecycle event
            channel.createSticky('onDestroy');

            // Channels that must fire before "deviceready" is fired.
            channel.waitForInitialization('onSnappReady');
            channel.waitForInitialization('onDOMContentLoaded');

            module.exports = channel;

        });

    // file: src/android/exec.js
    define("snapp/exec",
        function (require, exports, module) {

            /**
             * Execute a snapp command.  It is up to the native side whether this action
             * is synchronous or asynchronous.  The native side can return:
             *      Synchronous: PluginResult object as a JSON string
             *      Asynchronous: Empty string ""
             * If async, the native side will snapp.callbackSuccess or snapp.callbackError,
             * depending upon the result of the action.
             *
             * @param {Function} success    The success callback
             * @param {Function} fail       The fail callback
             * @param {String} service      The name of the service to use
             * @param {String} action       Action to be run in snapp
             * @param {String[]} [args]     Zero or more arguments to pass to the method
             */
            var snapp = require('snapp'),
                nativeApiProvider = require('snapp/android/nativeapiprovider'),
                utils = require('snapp/utils'),
                base64 = require('snapp/base64'),
                channel = require('snapp/channel'),
                jsToNativeModes = {
                    PROMPT: 0,
                    JS_OBJECT: 1
                },
                nativeToJsModes = {
                    // Polls for messages using the JS->Native bridge.
                    POLLING: 0,
                    // For LOAD_URL to be viable, it would need to have a work-around for
                    // the bug where the soft-keyboard gets dismissed when a message is sent.
                    LOAD_URL: 1,
                    // For the ONLINE_EVENT to be viable, it would need to intercept all event
                    // listeners (both through addEventListener and window.ononline) as well
                    // as set the navigator property itself.
                    ONLINE_EVENT: 2,
                    // Uses reflection to access private APIs of the WebView that can send JS
                    // to be executed.
                    // Requires Android 3.2.4 or above.
                    PRIVATE_API: 3
                },
                jsToNativeBridgeMode,
            // Set lazily.
                nativeToJsBridgeMode = nativeToJsModes.LOAD_URL,
                pollEnabled = false,
                messagesFromNative = [],
                bridgeSecret = -1;

            function androidExec(success, fail, service, action, args) {
                if (bridgeSecret < 0) {
                    // If we ever catch this firing, we'll need to queue up exec()s
                    // and fire them once we get a secret. For now, I don't think
                    // it's possible for exec() to be called since plugins are parsed but
                    // not run until until after onNativeReady.
                    throw new Error('exec() called without bridgeSecret');
                }
                // Set default bridge modes if they have not already been set.
                // By default, we use the failsafe, since addJavascriptInterface breaks too often
                if (jsToNativeBridgeMode === undefined) {
                    androidExec.setJsToNativeBridgeMode(jsToNativeModes.JS_OBJECT);
                }

                // Process any ArrayBuffers in the args into a string.
                for (var i = 0; i < args.length; i++) {
                    if (utils.typeName(args[i]) == 'ArrayBuffer') {
                        args[i] = base64.fromArrayBuffer(args[i]);
                    }
                }

                var callbackId = service + snapp.callbackId++,
                    argsJson = JSON.stringify(args);

                if (success || fail) {
                    snapp.callbacks[callbackId] = {
                        success: success,
                        fail: fail
                    };
                }
                var messages = nativeApiProvider.get().exec(bridgeSecret, service, action, callbackId, argsJson);
                // If argsJson was received by Java as null, try again with the PROMPT bridge mode.
                // This happens in rare circumstances, such as when certain Unicode characters are passed over the bridge on a Galaxy S2.  See CB-2666.
                if (jsToNativeBridgeMode == jsToNativeModes.JS_OBJECT && messages === "@Null arguments.") {
                    androidExec.setJsToNativeBridgeMode(jsToNativeModes.PROMPT);
                    androidExec(success, fail, service, action, args);
                    androidExec.setJsToNativeBridgeMode(jsToNativeModes.JS_OBJECT);
                    return;
                } else {
                    androidExec.processMessages(messages, true);
                }
            }


            androidExec.init = function () {
                var utils = require('snapp/utils');

                /*
                 原代码为
                 bridgeSecret = + (utils.prompt('', 'snapp_init:' + nativeToJsBridgeMode));
                 */
                bridgeSecret = +(utils.prompt('', 'snapp_init:' + nativeToJsBridgeMode));
                channel.onNativeReady.fire();
            };

            function pollOnceFromOnlineEvent() {
                pollOnce(true);
            }

            function pollOnce(opt_fromOnlineEvent) {
                if (bridgeSecret < 0) {
                    // This can happen when the NativeToJsMessageQueue resets the online state on page transitions.
                    // We know there's nothing to retrieve, so no need to poll.
                    return;
                }
                var msg = nativeApiProvider.get().retrieveJsMessages(bridgeSecret, !!opt_fromOnlineEvent);
                androidExec.processMessages(msg);
            }

            function pollingTimerFunc() {
                if (pollEnabled) {
                    pollOnce();
                    setTimeout(pollingTimerFunc, 50);
                }
            }

            function hookOnlineApis() {
                function proxyEvent(e) {
                    snapp.fireWindowEvent(e.type);
                }

                // The network module takes care of firing online and offline events.
                // It currently fires them only on document though, so we bridge them
                // to window here (while first listening for exec()-releated online/offline
                // events).
                window.addEventListener('online', pollOnceFromOnlineEvent, false);
                window.addEventListener('offline', pollOnceFromOnlineEvent, false);
                snapp.addWindowEventHandler('online');
                snapp.addWindowEventHandler('offline');
                document.addEventListener('online', proxyEvent, false);
                document.addEventListener('offline', proxyEvent, false);
            }

            hookOnlineApis();

            androidExec.jsToNativeModes = jsToNativeModes;
            androidExec.nativeToJsModes = nativeToJsModes;

            androidExec.setJsToNativeBridgeMode = function (mode) {
                if (mode == jsToNativeModes.JS_OBJECT && !window._snappNative) {
                    mode = jsToNativeModes.PROMPT;
                }
                nativeApiProvider.setPreferPrompt(mode == jsToNativeModes.PROMPT);
                jsToNativeBridgeMode = mode;
            };

            androidExec.setNativeToJsBridgeMode = function (mode) {
                if (mode == nativeToJsBridgeMode) {
                    return;
                }
                if (nativeToJsBridgeMode == nativeToJsModes.POLLING) {
                    pollEnabled = false;
                }

                nativeToJsBridgeMode = mode;
                // Tell the native side to switch modes.
                // Otherwise, it will be set by androidExec.init()
                if (bridgeSecret >= 0) {
                    nativeApiProvider.get().setNativeToJsBridgeMode(bridgeSecret, mode);
                }

                if (mode == nativeToJsModes.POLLING) {
                    pollEnabled = true;
                    setTimeout(pollingTimerFunc, 1);
                }
            };

            // Processes a single message, as encoded by NativeToJsMessageQueue.java.
            function processMessage(message) {
                try {
                    var firstChar = message.charAt(0);
                    if (firstChar == 'J') {
                        eval(message.slice(1));
                    } else if (firstChar == 'S' || firstChar == 'F') {
                        var success = firstChar == 'S';
                        var keepCallback = message.charAt(1) == '1';
                        var spaceIdx = message.indexOf(' ', 2);
                        var status = +message.slice(2, spaceIdx);
                        var nextSpaceIdx = message.indexOf(' ', spaceIdx + 1);
                        var callbackId = message.slice(spaceIdx + 1, nextSpaceIdx);
                        var payloadKind = message.charAt(nextSpaceIdx + 1);
                        var payload;
                        if (payloadKind == 's') {
                            payload = message.slice(nextSpaceIdx + 2);
                        } else if (payloadKind == 't') {
                            payload = true;
                        } else if (payloadKind == 'f') {
                            payload = false;
                        } else if (payloadKind == 'N') {
                            payload = null;
                        } else if (payloadKind == 'n') {
                            payload = +message.slice(nextSpaceIdx + 2);
                        } else if (payloadKind == 'A') {
                            var data = message.slice(nextSpaceIdx + 2);
                            var bytes = window.atob(data);
                            var arraybuffer = new Uint8Array(bytes.length);
                            for (var i = 0; i < bytes.length; i++) {
                                arraybuffer[i] = bytes.charCodeAt(i);
                            }
                            payload = arraybuffer.buffer;
                        } else if (payloadKind == 'S') {
                            payload = window.atob(message.slice(nextSpaceIdx + 2));
                        } else {
                            payload = JSON.parse(message.slice(nextSpaceIdx + 1));
                        }
                        snapp.callbackFromNative(callbackId, success, status, [payload], keepCallback);
                    } else {
                        console.log("processMessage failed: invalid message: " + JSON.stringify(message));
                    }
                } catch (e) {
                    console.log("processMessage failed: Error: " + e);
                    console.log("processMessage failed: Stack: " + e.stack);
                    console.log("processMessage failed: Message: " + message);
                }
            }

            var isProcessing = false;

            // This is called from the NativeToJsMessageQueue.java.
            androidExec.processMessages = function (messages, opt_useTimeout) {
                if (messages) {
                    messagesFromNative.push(messages);
                }
                // Check for the reentrant case.
                if (isProcessing) {
                    return;
                }
                if (opt_useTimeout) {
                    window.setTimeout(androidExec.processMessages, 0);
                    return;
                }
                isProcessing = true;
                try {
                    // TODO: add setImmediate polyfill and process only one message at a time.
                    while (messagesFromNative.length) {
                        var msg = popMessageFromQueue();
                        console.log(msg)
                        // The Java side can send a * message to indicate that it
                        // still has messages waiting to be retrieved.
                        if (msg == '*' && messagesFromNative.length === 0) {
                            setTimeout(pollOnce, 0);
                            return;
                        }
                        processMessage(msg);
                    }
                } finally {
                    isProcessing = false;
                }
            };

            function popMessageFromQueue() {
                var messageBatch = messagesFromNative.shift();
                // if (typeof(messageBatch) == undefined){
                // 	return;
                // }
                if (messageBatch == '*') {
                    return '*';
                }

                var spaceIdx = messageBatch.indexOf(' ');
                var msgLen = +messageBatch.slice(0, spaceIdx);
                var message = messageBatch.substr(spaceIdx + 1, msgLen);
                messageBatch = messageBatch.slice(spaceIdx + msgLen + 1);
                // if (messageBatch) {
                // 	messagesFromNative.unshift(messageBatch);
                // }
                console.log(message)
                return message;
            }

            module.exports = androidExec;

        });

    // file: src/common/exec/proxy.js
    define("snapp/exec/proxy",
        function (require, exports, module) {

            // internal map of proxy function
            var CommandProxyMap = {};

            module.exports = {

                // example: snapp.commandProxy.add("Accelerometer",{getCurrentAcceleration: function(successCallback, errorCallback, options) {...},...);
                add: function (id, proxyObj) {
                    console.log("adding proxy for " + id);
                    CommandProxyMap[id] = proxyObj;
                    return proxyObj;
                },

                // snapp.commandProxy.remove("Accelerometer");
                remove: function (id) {
                    var proxy = CommandProxyMap[id];
                    delete CommandProxyMap[id];
                    CommandProxyMap[id] = null;
                    return proxy;
                },

                get: function (service, action) {
                    return (CommandProxyMap[service] ? CommandProxyMap[service][action] : null);
                }
            };
        });

    // file: src/common/init.js
    define("snapp/init",
        function (require, exports, module) {

            var channel = require('snapp/channel');
            var snapp = require('snapp');
            var modulemapper = require('snapp/modulemapper');
            var platform = require('snapp/platform');
            var pluginloader = require('snapp/pluginloader');

            // 定义平台初期化处理必须在onNativeReady和onPluginsReady之后进行
            var platformInitChannelsArray = [channel.onNativeReady, channel.onPluginsReady];
            // 输出事件通道名到日志
            function logUnfiredChannels(arr) {
                for (var i = 0; i < arr.length; ++i) {
                    if (arr[i].state != 2) {
                        console.log('Channel not fired: ' + arr[i].type);
                    }
                }
            }

            // 5秒之后deviceready事件还没有被调用将输出log提示
            // 出现这个错误的情况比较复杂，比如，加载的plugin太多等等
            window.setTimeout(function () {
                    if (channel.onDeviceReady.state != 2) {
                        console.log('deviceready has not fired after 5 seconds.');
                        logUnfiredChannels(platformInitChannelsArray);
                        logUnfiredChannels(channel.deviceReadyChannelsArray);
                    }
                },
                5000);

            // Replace navigator before any modules are required(), to ensure it happens as soon as possible.
            // We replace it so that properties that can't be clobbered can instead be overridden.
            // 替换window.navigator
            function replaceNavigator(origNavigator) {
                // 定义新的navigator，把navigator的原型链赋给新的navigator的原型链
                var SnappNavigator = function () {
                };
                SnappNavigator.prototype = origNavigator;
                var newNavigator = new SnappNavigator();
                // This work-around really only applies to new APIs that are newer than Function.bind.
                // Without it, APIs such as getGamepads() break.
                // 判断是否存在Function.bind函数
                if (SnappNavigator.bind) {
                    for (var key in origNavigator) {
                        if (typeof origNavigator[key] == 'function') {
                            // 通过bind创建一个新的函数（this指向navigator）后赋给新的navigator
                            // 参考：https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/bind
                            newNavigator[key] = origNavigator[key].bind(origNavigator);
                        } else {
                            (function (k) {
                                Object.defineProperty(newNavigator, k, {
                                    get: function () {
                                        return origNavigator[k];
                                    },
                                    configurable: true,
                                    enumerable: true
                                });
                            })(key);
                        }
                    }
                }
                return newNavigator;
            }

            // 替换webview的BOM对象navigator
            // Snapp提供的接口基本都是：navigator.<plugin_name>.<action_name>
            if (window.navigator) {
                window.navigator = replaceNavigator(window.navigator);
            }

            // 定义console.log()
            if (!window.console) {
                window.console = {
                    log: function () {
                    }
                };
            }

            // 定义console.warn()
            if (!window.console.warn) {
                window.console.warn = function (msg) {
                    this.log("warn: " + msg);
                };
            }

            // 注册pause，resume，deviceready事件通道，并应用到Snapp自定义的事件拦截
            // 这样页面定义的事件监听器就能订阅到相应的通道上了。
            // Register pause, resume and deviceready channels as events on document.
            channel.onPause = snapp.addDocumentEventHandler('pause');
            channel.onResume = snapp.addDocumentEventHandler('resume');
            channel.onDeviceReady = snapp.addStickyDocumentEventHandler('deviceready');

            // Listen for DOMContentLoaded and notify our channel subscribers.
            // 如果此时DOM加载完成，触发onDOMContentLoaded事件通道中的事件处理
            if (document.readyState == 'complete' || document.readyState == 'interactive') {
                channel.onDOMContentLoaded.fire();
            } else {
                // 如果此时DOM没有加载完成，定义一个监听器在DOM完成后触发事件通道的处理
                // 注意这里调用的webview的原生事件监听
                document.addEventListener('DOMContentLoaded',
                    function () {
                        channel.onDOMContentLoaded.fire();
                    },
                    false);
            }

            // _nativeReady is global variable that the native side can set
            // to signify that the native code is ready. It is a global since
            // it may be called before any snapp JS is ready.
            if (window._nativeReady) {
                channel.onNativeReady.fire();
            }

            // 给常用的模块起个别名
            // 比如：就可以直接使用snapp.exec(...)来代替 exec = require('snapp/exec'); exec(...);
            // 不过第一行第二个参数应该是“Snapp”，s应该大写！！！
            modulemapper.clobbers('snapp', 'snapp');
            modulemapper.clobbers('snapp/exec', 'snapp.exec');
            modulemapper.clobbers('snapp/exec', 'Snapp.exec');

            // 调用平台初始化启动处理
            platform.bootstrap && platform.bootstrap();

            // 所有插件加载完成后，触发onPluginsReady事件通道中的事件处理
            setTimeout(function () {
                    pluginloader.load(function () {
                        channel.onPluginsReady.fire();
                    });
                },
                0);

            // 一旦本地代码准备就绪，创建snapp所需的所有对象
            channel.join(function () {
                    // 把所有模块附加到window对象上
                    modulemapper.mapModules(window);

                    // 如果平台有特殊的初始化处理，调用它（目前来看都没有）
                    platform.initialize && platform.initialize();

                    // 触发onSnappReady事件通道，标示snapp准备完成
                    channel.onSnappReady.fire();

                    // 一切准备就绪后，执行deviceready事件通道上的所有事件。
                    channel.join(function () {
                            require('snapp').fireDocumentEvent('deviceready');
                        },
                        channel.deviceReadyChannelsArray);

                },
                platformInitChannelsArray);

        });

    // file: src/common/modulemapper.js
    define("snapp/modulemapper",
        function (require, exports, module) {

            var builder = require('snapp/builder'),
                moduleMap = define.moduleMap,
                symbolList,
                deprecationMap;

            exports.reset = function () {
                symbolList = [];
                deprecationMap = {};
            };

            function addEntry(strategy, moduleName, symbolPath, opt_deprecationMessage) {
                if (!(moduleName in moduleMap)) {
                    throw new Error('Module ' + moduleName + ' does not exist.');
                }
                symbolList.push(strategy, moduleName, symbolPath);
                if (opt_deprecationMessage) {
                    deprecationMap[symbolPath] = opt_deprecationMessage;
                }
            }

            // Note: Android 2.3 does have Function.bind().
            exports.clobbers = function (moduleName, symbolPath, opt_deprecationMessage) {
                addEntry('c', moduleName, symbolPath, opt_deprecationMessage);
            };

            exports.merges = function (moduleName, symbolPath, opt_deprecationMessage) {
                addEntry('m', moduleName, symbolPath, opt_deprecationMessage);
            };

            exports.defaults = function (moduleName, symbolPath, opt_deprecationMessage) {
                addEntry('d', moduleName, symbolPath, opt_deprecationMessage);
            };

            exports.runs = function (moduleName) {
                addEntry('r', moduleName, null);
            };

            function prepareNamespace(symbolPath, context) {
                if (!symbolPath) {
                    return context;
                }
                var parts = symbolPath.split('.');
                var cur = context;
                for (var i = 0,
                         part; part = parts[i]; ++i) {
                    cur = cur[part] = cur[part] || {};
                }
                return cur;
            }

            exports.mapModules = function (context) {
                var origSymbols = {};
                context.CDV_origSymbols = origSymbols;
                for (var i = 0,
                         len = symbolList.length; i < len; i += 3) {
                    var strategy = symbolList[i];
                    var moduleName = symbolList[i + 1];
                    var module = require(moduleName);
                    // <runs/>
                    if (strategy == 'r') {
                        continue;
                    }
                    var symbolPath = symbolList[i + 2];
                    var lastDot = symbolPath.lastIndexOf('.');
                    var namespace = symbolPath.substr(0, lastDot);
                    var lastName = symbolPath.substr(lastDot + 1);

                    var deprecationMsg = symbolPath in deprecationMap ? 'Access made to deprecated symbol: ' + symbolPath + '. ' + deprecationMsg : null;
                    var parentObj = prepareNamespace(namespace, context);
                    var target = parentObj[lastName];

                    if (strategy == 'm' && target) {
                        builder.recursiveMerge(target, module);
                    } else if ((strategy == 'd' && !target) || (strategy != 'd')) {
                        if (!(symbolPath in origSymbols)) {
                            origSymbols[symbolPath] = target;
                        }
                        builder.assignOrWrapInDeprecateGetter(parentObj, lastName, module, deprecationMsg);
                    }
                }
            };

            exports.getOriginalSymbol = function (context, symbolPath) {
                var origSymbols = context.CDV_origSymbols;
                if (origSymbols && (symbolPath in origSymbols)) {
                    return origSymbols[symbolPath];
                }
                var parts = symbolPath.split('.');
                var obj = context;
                for (var i = 0; i < parts.length; ++i) {
                    obj = obj && obj[parts[i]];
                }
                return obj;
            };

            exports.reset();

        });

    // file: src/android/platform.js
    define("snapp/platform",
        function (require, exports, module) {

            module.exports = {
                id: 'android',
                bootstrap: function () {
                    var channel = require('snapp/channel'),
                        snapp = require('snapp'),
                        exec = require('snapp/exec'),
                        modulemapper = require('snapp/modulemapper');

                    // Get the shared secret needed to use the bridge.
                    exec.init();

                    // TODO: Extract this as a proper plugin.
                    modulemapper.clobbers('snapp/plugin/android/app', 'app');

                    // Add hardware MENU and SEARCH button handlers
                    snapp.addDocumentEventHandler('menubutton');
                    snapp.addDocumentEventHandler('searchbutton');

                    function bindButtonChannel(buttonName) {
                        // generic button bind used for volumeup/volumedown buttons
                        var volumeButtonChannel = snapp.addDocumentEventHandler(buttonName + 'button');
                        volumeButtonChannel.onHasSubscribersChange = function () {
                            exec(null, null, "App", "overrideButton", [buttonName, this.numHandlers == 1]);
                        };
                    }

                    // Inject a listener for the volume buttons on the document.
                    bindButtonChannel('volumeup');
                    bindButtonChannel('volumedown');

                    // Let native code know we are all done on the JS side.
                    // Native code will then un-hide the WebView.
                    channel.onSnappReady.subscribe(function () {

                    });
                }
            };

        });

    // file: src/android/plugin/android/app.js
    define("snapp/plugin/android/app",
        function (require, exports, module) {

            var exec = require('snapp/exec');

            module.exports = {
                /**
                 * Clear the resource cache.
                 */
                clearCache: function () {
                    exec(null, null, "App", "clearCache", []);
                },

                /**
                 * Clear web history in this web view.
                 * Instead of BACK button loading the previous web page, it will exit the app.
                 */
                clearHistory: function () {
                    exec(null, null, "App", "clearHistory", []);
                },

                /**
                 * Go to previous page displayed.
                 * This is the same as pressing the backbutton on Android device.
                 */
                backHistory: function () {
                    exec(null, null, "App", "backHistory", []);
                },

                /**
                 * Override the default behavior of the Android back button.
                 * If overridden, when the back button is pressed, the "backKeyDown" JavaScript event will be fired.
                 *
                 * Note: The user should not have to call this method.  Instead, when the user
                 *       registers for the "backbutton" event, this is automatically done.
                 *
                 * @param override        T=override, F=cancel override
                 */
                overrideBackbutton: function (override) {
                    exec(null, null, "App", "overrideBackbutton", [override]);
                },

                /**
                 * Override the default behavior of the Android volume button.
                 * If overridden, when the volume button is pressed, the "volume[up|down]button"
                 * JavaScript event will be fired.
                 *
                 * Note: The user should not have to call this method.  Instead, when the user
                 *       registers for the "volume[up|down]button" event, this is automatically done.
                 *
                 * @param button          volumeup, volumedown
                 * @param override        T=override, F=cancel override
                 */
                overrideButton: function (button, override) {
                    exec(null, null, "App", "overrideButton", [button, override]);
                },

                /**
                 * Exit and terminate the application.
                 */
                close: function () {
                    return exec(null, null, "App", "close", []);
                }
            };

        });

    // file: src/common/pluginloader.js
    define("snapp/pluginloader",
        function (require, exports, module) {

            var modulemapper = require('snapp/modulemapper');
            var urlutil = require('snapp/urlutil');

            // Helper function to inject a <script> tag.
            // Exported for testing.
            exports.injectScript = function (url, onload, onerror) {
                var script = document.createElement("script");
                // onload fires even when script fails loads with an error.
                script.onload = onload;
                // onerror fires for malformed URLs.
                script.onerror = onerror;
                script.src = url;
                document.head.appendChild(script);
            };

            function injectIfNecessary(id, url, onload, onerror) {
                onerror = onerror || onload;
                if (id in define.moduleMap) {
                    onload();
                } else {
                    exports.injectScript(url,
                        function () {
                            if (id in define.moduleMap) {
                                onload();
                            } else {
                                onerror();
                            }
                        },
                        onerror);
                }
            }

            function onScriptLoadingComplete(moduleList, finishPluginLoading) {
                // Loop through all the plugins and then through their clobbers and merges.
                for (var i = 0,
                         module; module = moduleList[i]; i++) {
                    //  把该模块需要clobber的clobber到指定的clobbers里
                    if (module.clobbers && module.clobbers.length) {
                        for (var j = 0; j < module.clobbers.length; j++) {
                            modulemapper.clobbers(module.id, module.clobbers[j]);
                        }
                    }

                    // 把该模块需要合并的部分合并到指定的模块里
                    if (module.merges && module.merges.length) {
                        for (var k = 0; k < module.merges.length; k++) {
                            modulemapper.merges(module.id, module.merges[k]);
                        }
                    }

                    // Finally, if runs is truthy we want to simply require() the module.
                    // 处理只希望require()的模块
                    if (module.runs) {
                        modulemapper.runs(module.id);
                    }
                }

                finishPluginLoading();
            }

            // Handler for the snapp_plugins.js content.
            // See plugman's plugin_loader.js for the details of this object.
            // This function is only called if the really is a plugins array that isn't empty.
            // Otherwise the onerror response handler will just call finishPluginLoading().
            function handlePluginsObject(path, moduleList, finishPluginLoading) {
                // Now inject the scripts.
                var scriptCounter = moduleList.length;

                if (!scriptCounter) {
                    finishPluginLoading();
                    return;
                }

                function scriptLoadedCallback() {
                    if (!--scriptCounter) {
                        onScriptLoadingComplete(moduleList, finishPluginLoading);
                    }
                }

                for (var i = 0; i < moduleList.length; i++) {
                    injectIfNecessary(moduleList[i].id, path + moduleList[i].file, scriptLoadedCallback);
                }
            }

            function findSnappPath() {
                var path = null;
                var scripts = document.getElementsByTagName('script');
                var term = '/sneapp.js';
                for (var n = scripts.length - 1; n > -1; n--) {
                    var src = scripts[n].src.replace(/\?.*$/, ''); // Strip any query param (CB-6007).
                    if (src.indexOf(term) == (src.length - term.length)) {
                        path = src.substring(0, src.length - term.length) + '/';
                        break;
                    }
                }
                return path;
            }

            // Tries to load all plugins' js-modules.
            // This is an async process, but onDeviceReady is blocked on onPluginsReady.
            // onPluginsReady is fired when there are no plugins to load, or they are all done.
            // 加载所有snapp_plugins.js中定义的js-module
            exports.load = function (callback) {
                var pathPrefix = findSnappPath();
                if (pathPrefix === null) {
                    console.log('Could not find sneapp.js script tag. Plugin loading may fail.');
                    pathPrefix = '';
                }
                injectIfNecessary('snapp/plugin_list', pathPrefix + 'snapp_plugins.js',
                    function () {
                        var moduleList = require("snapp/plugin_list");
                        handlePluginsObject(pathPrefix, moduleList, callback);
                    },
                    callback);
            };

        });

    // file: src/common/urlutil.js
    define("snapp/urlutil",
        function (require, exports, module) {

            /**
             * For already absolute URLs, returns what is passed in.
             * For relative URLs, converts them to absolute ones.
             */
            exports.makeAbsolute = function makeAbsolute(url) {
                var anchorEl = document.createElement('a');
                anchorEl.href = url;
                return anchorEl.href;
            };

        });

    // file: src/common/utils.js
    define("snapp/utils",
        function (require, exports, module) {

            var utils = exports;

            /**
             * Defines a property getter / setter for obj[key].
             */
            utils.defineGetterSetter = function (obj, key, getFunc, opt_setFunc) {
                if (Object.defineProperty) {
                    var desc = {
                        get: getFunc,
                        configurable: true
                    };
                    if (opt_setFunc) {
                        desc.set = opt_setFunc;
                    }
                    Object.defineProperty(obj, key, desc);
                } else {
                    obj.__defineGetter__(key, getFunc);
                    if (opt_setFunc) {
                        obj.__defineSetter__(key, opt_setFunc);
                    }
                }
            };

            /**
             * Defines a property getter for obj[key].
             */
            utils.defineGetter = utils.defineGetterSetter;

            utils.arrayIndexOf = function (a, item) {
                if (a.indexOf) {
                    return a.indexOf(item);
                }
                var len = a.length;
                for (var i = 0; i < len; ++i) {
                    if (a[i] == item) {
                        return i;
                    }
                }
                return -1;
            };

            /**
             * Returns whether the item was found in the array.
             */
            utils.arrayRemove = function (a, item) {
                var index = utils.arrayIndexOf(a, item);
                if (index != -1) {
                    a.splice(index, 1);
                }
                return index != -1;
            };

            utils.typeName = function (val) {
                return Object.prototype.toString.call(val).slice(8, -1);
            };

            /**
             * Returns an indication of whether the argument is an array or not
             */
            utils.isArray = function (a) {
                return utils.typeName(a) == 'Array';
            };

            /**
             * Returns an indication of whether the argument is a Date or not
             */
            utils.isDate = function (d) {
                return utils.typeName(d) == 'Date';
            };

            /**
             * Does a deep clone of the object.
             */
            utils.clone = function (obj) {
                if (!obj || typeof obj == 'function' || utils.isDate(obj) || typeof obj != 'object') {
                    return obj;
                }

                var retVal, i;

                if (utils.isArray(obj)) {
                    retVal = [];
                    for (i = 0; i < obj.length; ++i) {
                        retVal.push(utils.clone(obj[i]));
                    }
                    return retVal;
                }

                retVal = {};
                for (i in obj) {
                    if (!(i in retVal) || retVal[i] != obj[i]) {
                        retVal[i] = utils.clone(obj[i]);
                    }
                }
                return retVal;
            };

            /**
             * Returns a wrapped version of the function
             */
            utils.close = function (context, func, params) {
                if (typeof params == 'undefined') {
                    return function () {
                        return func.apply(context, arguments);
                    };
                } else {
                    return function () {
                        return func.apply(context, params);
                    };
                }
            };

            /**
             * Create a UUID
             */
            utils.createUUID = function () {
                return UUIDcreatePart(4) + '-' + UUIDcreatePart(2) + '-' + UUIDcreatePart(2) + '-' + UUIDcreatePart(2) + '-' + UUIDcreatePart(6);
            };

            /**
             * Extends a child object from a parent object using classical inheritance
             * pattern.
             */
            utils.extend = (function () {
                // proxy used to establish prototype chain
                var F = function () {
                };
                // extend Child from Parent
                return function (Child, Parent) {
                    F.prototype = Parent.prototype;
                    Child.prototype = new F();
                    Child.__super__ = Parent.prototype;
                    Child.prototype.constructor = Child;
                };
            }());

            /**
             * Alerts a message in any available way: alert or console.log.
             */
            utils.alert = function (msg) {
                if (window.alert) {
                    window.alert(msg);
                } else if (console && console.log) {
                    console.log(msg);
                }
            };
            utils.prompt = function (msg, value) {
                var userAgent = window.navigator.userAgent;
                if (userAgent.match(/SNEBUY-APP?/i)) {
                    var version = utils.getVersion(userAgent);
                    if (version > 81) {
                        return window.prompt(msg, value);
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            };
            utils.getVersion = function (userAgent) {
                var reApp = new RegExp("SNEBUY-APP \\d+");
                var result = userAgent.match(reApp);

                result = result + "";
                var red = new RegExp("\\d+");
                result = result.match(red);

                return result;
            };

            //------------------------------------------------------------------------------
            function UUIDcreatePart(length) {
                var uuidpart = "";
                for (var i = 0; i < length; i++) {
                    var uuidchar = parseInt((Math.random() * 256), 10).toString(16);
                    if (uuidchar.length == 1) {
                        uuidchar = "0" + uuidchar;
                    }
                    uuidpart += uuidchar;
                }
                return uuidpart;
            }

        });

    define("snapp/version",
        function (require, exports, module) {

            var version = {
                version_32: 84,
                version_33: 90,
                version_34: 92,
            };

            module.exports = version;

        });

    window.snapp = require('snapp');
    // file: src/scripts/bootstrap.js
    require('snapp/init');

})();
