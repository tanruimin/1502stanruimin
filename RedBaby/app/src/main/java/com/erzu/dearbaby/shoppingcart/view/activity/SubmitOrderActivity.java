package com.erzu.dearbaby.shoppingcart.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.shoppingcart.model.utils.MySiyao;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SubmitOrderActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.tv_promotion_notice_show)
    TextView tvPromotionNoticeShow;
    @BindView(R.id.iv_promotion_notice_close)
    ImageView ivPromotionNoticeClose;
    @BindView(R.id.tv_cart2_no_delivery)
    TextView tvCart2NoDelivery;
    @BindView(R.id.tv_cart2_delivery_user)
    TextView tvCart2DeliveryUser;
    @BindView(R.id.tv_cart2_delivery_phone)
    TextView tvCart2DeliveryPhone;
    @BindView(R.id.tv_cart2_delivery_address)
    TextView tvCart2DeliveryAddress;
    @BindView(R.id.ll_cart2_delivery)
    LinearLayout llCart2Delivery;
    @BindView(R.id.tv_cart2_support_pick)
    TextView tvCart2SupportPick;
    @BindView(R.id.iv_cart2_delivery_arrow)
    ImageView ivCart2DeliveryArrow;
    @BindView(R.id.rl_cart2_delivery)
    RelativeLayout rlCart2Delivery;
    @BindView(R.id.tv_cart2_delivery_id_number)
    TextView tvCart2DeliveryIdNumber;
    @BindView(R.id.et_cart2_delivery_id_num)
    EditText etCart2DeliveryIdNum;
    @BindView(R.id.ll_cart2_delivery_id_number)
    RelativeLayout llCart2DeliveryIdNumber;
    @BindView(R.id.solv_cart2)
    View solvCart2;
    @BindView(R.id.tv_pay_name)
    TextView tvPayName;
    @BindView(R.id.tv_cart2_pay_select)
    TextView tvCart2PaySelect;
    @BindView(R.id.tv_pay_msg)
    TextView tvPayMsg;
    @BindView(R.id.iv_pay)
    ImageView ivPay;
    @BindView(R.id.ll_cart2_pay_mode)
    RelativeLayout llCart2PayMode;
    @BindView(R.id.tv_pay_interest)
    TextView tvPayInterest;
    @BindView(R.id.tv_pay_periods)
    TextView tvPayPeriods;
    @BindView(R.id.rl_pay_periods)
    RelativeLayout rlPayPeriods;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_coupon_amount)
    TextView tvCouponAmount;
    @BindView(R.id.tv_coupon_prompt)
    TextView tvCouponPrompt;
    @BindView(R.id.tv_coupon_prompt1)
    TextView tvCouponPrompt1;
    @BindView(R.id.iv_coupon_arrow)
    ImageView ivCouponArrow;
    @BindView(R.id.rl_coupon)
    RelativeLayout rlCoupon;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_card_amount)
    TextView tvCardAmount;
    @BindView(R.id.tv_card_prompt)
    TextView tvCardPrompt;
    @BindView(R.id.tv_card_prompt1)
    TextView tvCardPrompt1;
    @BindView(R.id.iv_card_arrow)
    ImageView ivCardArrow;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.tv_cloudDiamond)
    TextView tvCloudDiamond;
    @BindView(R.id.cb_cloudDiamond)
    CheckBox cbCloudDiamond;
    @BindView(R.id.rl_cloudDiamond)
    RelativeLayout rlCloudDiamond;
    @BindView(R.id.tv_invoice_text)
    TextView tvInvoiceText;
    @BindView(R.id.rl_invoice)
    RelativeLayout rlInvoice;
    @BindView(R.id.tv_others_pay)
    TextView tvOthersPay;
    @BindView(R.id.cb_others_pay)
    ImageView cbOthersPay;
    @BindView(R.id.rl_others_pay)
    RelativeLayout rlOthersPay;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.cb_protocol)
    ImageView cbProtocol;
    @BindView(R.id.rl_protocol)
    RelativeLayout rlProtocol;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @BindView(R.id.rl_discount)
    RelativeLayout rlDiscount;
    @BindView(R.id.tv_energy_price)
    TextView tvEnergyPrice;
    @BindView(R.id.rl_energy_price)
    RelativeLayout rlEnergyPrice;
    @BindView(R.id.tv_ship_price)
    TextView tvShipPrice;
    @BindView(R.id.tv_tax_price)
    TextView tvTaxPrice;
    @BindView(R.id.rl_tax_price)
    RelativeLayout rlTaxPrice;
    @BindView(R.id.ll_inner)
    LinearLayout llInner;
    @BindView(R.id.sv_root)
    ScrollView svRoot;
    @BindView(R.id.tv_cart2_pop_delivery_address)
    TextView tvCart2PopDeliveryAddress;
    @BindView(R.id.rl_cart2_pop_address)
    RelativeLayout rlCart2PopAddress;
    @BindView(R.id.tv_cart2_total_price)
    TextView tvCart2TotalPrice;
    @BindView(R.id.tv_cart2_submit)
    TextView tvCart2Submit;
    @BindView(R.id.rl_order_submit)
    RelativeLayout rlOrderSubmit;

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017052307321911";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = MySiyao.SiYao;
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(SubmitOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(SubmitOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(SubmitOrderActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(SubmitOrderActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        ButterKnife.bind(this);
        inidClicklitener();

    }

    private void inidClicklitener() {
        tvCart2Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cart2_submit:
                if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
                    new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
                    return;
                }

                /**
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo的获取必须来自服务端；
                 */
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(SubmitOrderActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
        }
    }
}
