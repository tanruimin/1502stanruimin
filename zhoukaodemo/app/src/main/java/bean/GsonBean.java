package bean;

import java.util.List;

/**
 * data:2017/4/14
 * name:tanruimin tanruimin
 * function:
 */

public class GsonBean {
    /**
     * status : 10000
     * data : [{"id":"131","brand_id":"53","title":"(同仁堂) 当归苦参丸","apply":"凉血，祛湿。用于血燥湿热引起：头面生疮，粉刺疙瘩，湿疹刺痒，酒糟鼻赤。","buy_price":"15.48","old_price":"15.48","size":"6g*6瓶/盒","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/6a9e068cf64742af.jpeg","cate_id":"48","baike":"酒糟鼻，痤疮，湿疹","sale_num":"0","cart_num":0},{"id":"165","brand_id":"115","title":"(达仁堂) 京万红软膏","apply":"活血解毒，消肿止痛，去腐生肌。用于轻度水、火烫伤，疮疡肿痛，创面溃烂。","buy_price":"18.60","old_price":"18.60","size":"20g","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/b30d722b0e776688.jpg","cate_id":"49","baike":"肛周湿疹，烫伤，痔疮，肛裂","sale_num":"0","cart_num":0},{"id":"162","brand_id":"111","title":"(成天药业) 足光散","apply":"清热燥湿，杀虫敛汗。用于湿热下注所致的角化型手足癣及臭汗症。","buy_price":"10.00","old_price":"10.00","size":"40g*3包","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/65b4e7493ea55dbd.jpg","cate_id":"48","baike":"湿热，足癣，臭汗，湿热下注，手足癣，臭汗症","sale_num":"0","cart_num":0},{"id":"177","brand_id":"121","title":"(梁介福药业) 斧标正红花油","apply":"\u200b温经散寒，活血止痛。用于风湿骨痛，筋骨酸痛，扭伤瘀肿，跌打损伤，蚊虫叮咬。","buy_price":"16.80","old_price":"16.80","size":"22ml","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/f9b51dab26cae8d4.jpg","cate_id":"49","baike":"蚊子叮咬，风湿性疾病，运动创伤，韧带损伤","sale_num":"0","cart_num":0},{"id":"134","brand_id":"90","title":"(亚东生物) 肤痒颗粒","apply":"本品祛风活血，除湿止痒。本品用于皮肤搔痒病，荨麻疹。","buy_price":"18.75","old_price":"18.75","size":"每袋装9克","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/0d9cfbf91da8a9a4.jpg","cate_id":"49","baike":"皮肤瘙痒症，慢性荨麻疹，手湿疹，手部湿疹，荨麻疹，湿疹","sale_num":"2","cart_num":0},{"id":"174","brand_id":"54","title":"(马应龙药业) 龙珠软膏","apply":"清热解毒，消肿止痛，祛腐生肌。适用于疮疖、红、肿、热、痛及轻度烫伤。","buy_price":"25.80","old_price":"25.80","size":"15g","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/9bf44c403b28ce9c.jpg","cate_id":"47","baike":"烫伤，疖","sale_num":"0","cart_num":0},{"id":"129","brand_id":"88","title":"(玉林制药) 湿毒清胶囊","apply":"养血润燥，化湿解毒，祛风止痒。本品用于皮肤瘙痒症属血虚湿蕴皮肤证者。","buy_price":"23.16","old_price":"23.16","size":"0.5g*30粒/瓶","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/0f5d91aec26821df.jpg","cate_id":"48","baike":"皮肤瘙痒症","sale_num":"0","cart_num":0},{"id":"119","brand_id":"53","title":"(同仁堂)皮肤病血毒丸","apply":"清血解毒，消肿止痒。用于经络不和，湿热血燥引起的风疹，湿疹，皮肤刺痒，雀斑粉刺，面赤鼻齄，疮疡肿毒，脚气疥癣，头目眩晕，大便燥结。","buy_price":"16.73","old_price":"15.00","size":"0.15g*100s","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/8364fbbdd66b44ee.jpg","cate_id":"48","baike":"湿疹,雀斑,粉刺,","sale_num":"0","cart_num":0},{"id":"208","brand_id":"142","title":"(中联)熊胆痔疮膏","apply":"清热解毒、收湿敛疮。用于痔疮痛痒、肛门破裂、红肿流水","buy_price":"23.80","old_price":"23.80","size":"10g","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/2c5996c0c549d8d8.jpg","cate_id":"50","baike":"痔疮","sale_num":"0","cart_num":0},{"id":"207","brand_id":"116","title":"(云南白药)云南白药痔疮膏","apply":"化瘀止血，活血止痛，解毒消肿。用于内痔Ⅰ、Ⅱ、Ⅲ期及其混合痔之便血、痔粘膜改变，炎性外痔之红肿及痔疮之肛门肿痛等。","buy_price":"29.80","old_price":"29.80","size":"1.5g*6支","thumb":"https://ohdrjnjvc.qnssl.com/attached/drugs/df0f34b290aa63ea.jpg","cate_id":"50","baike":"外痔，内痔，混合痔，痔疮","sale_num":"0","cart_num":0}]
     * count : 58
     */

    private int status;
    private String count;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * id : 131
         * brand_id : 53
         * title : (同仁堂) 当归苦参丸
         * apply : 凉血，祛湿。用于血燥湿热引起：头面生疮，粉刺疙瘩，湿疹刺痒，酒糟鼻赤。
         * buy_price : 15.48
         * old_price : 15.48
         * size : 6g*6瓶/盒
         * thumb : https://ohdrjnjvc.qnssl.com/attached/drugs/6a9e068cf64742af.jpeg
         * cate_id : 48
         * baike : 酒糟鼻，痤疮，湿疹
         * sale_num : 0
         * cart_num : 0
         */

        private String id;
        private String brand_id;
        private String title;
        private String apply;
        private String buy_price;
        private String old_price;
        private String size;
        private String thumb;
        private String cate_id;
        private String baike;
        private String sale_num;
        private int cart_num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getApply() {
            return apply;
        }

        public void setApply(String apply) {
            this.apply = apply;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getBaike() {
            return baike;
        }

        public void setBaike(String baike) {
            this.baike = baike;
        }

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }
    }
}
