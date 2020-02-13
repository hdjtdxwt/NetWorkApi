package com.epsit.networkapi;

import java.util.List;

/**
 * 这里的类有点小问题的，就是返回的attributes属性都是 @attributes 这样的，需要先对返回的String处理一下先,所以发现有获取String类型数据的需求需要封装
 */
public class ZhouXunWeather {

    /**
     * attributes : {"dn":"nay"}
     * city : [{"attributes":{"cityX":"492","cityY":"100","cityname":"庆云县","centername":"庆云县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"南风","windPower":"1级","humidity":"94%","time":"19:00","url":"101120407"}},{"attributes":{"cityX":"438.25","cityY":"116.65","cityname":"乐陵市","centername":"乐陵市","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"东风","windPower":"1级","humidity":"94%","time":"19:00","url":"101120406"}},{"attributes":{"cityX":"374","cityY":"137","cityname":"宁津县","centername":"宁津县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"东风","windPower":"1级","humidity":"99%","time":"19:00","url":"101120409"}},{"attributes":{"cityX":"341.9","cityY":"205","cityname":"陵县","centername":"陵县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"东风","windPower":"1级","humidity":"100%","time":"19:00","url":"101120404"}},{"attributes":{"cityX":"283.05","cityY":"196.75","cityname":"德州市","centername":"德州市","fontColor":"FFFF00","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"东风","windPower":"1级","humidity":"98%","time":"19:00","url":"101120401"}},{"attributes":{"cityX":"405","cityY":"250","cityname":"临邑县","centername":"临邑县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"18","windState":"东北风微风级","windDir":"东南风","windPower":"1级","humidity":"95%","time":"19:00","url":"101120403"}},{"attributes":{"cityX":"306.35","cityY":"265.65","cityname":"平原县","centername":"平原县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"18","windState":"东北风微风级","windDir":"东风","windPower":"1级","humidity":"95%","time":"19:00","url":"101120408"}},{"attributes":{"cityX":"252","cityY":"252.6","cityname":"武城县","centername":"武城县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"暂无实况","windPower":"0级","humidity":"96%","time":"19:00","url":"101120402"}},{"attributes":{"cityX":"243","cityY":"320","cityname":"夏津县","centername":"夏津县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"18","windState":"东北风微风级","windDir":"北风","windPower":"2级","humidity":"95%","time":"19:00","url":"101120410"}},{"attributes":{"cityX":"353","cityY":"320","cityname":"禹城市","centername":"禹城市","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"18","windState":"东北风微风级","windDir":"东北风","windPower":"2级","humidity":"96%","time":"19:00","url":"101120411"}},{"attributes":{"cityX":"372","cityY":"376","cityname":"齐河县","centername":"齐河县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"18","windState":"东北风微风级","windDir":"东北风","windPower":"2级","humidity":"99%","time":"19:00","url":"101120405"}}]
     * error_code : 0
     */

    private AttributesBean attributes;
    private int error_code;
    private List<CityBean> city;

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class AttributesBean {
        /**
         * dn : nay
         */

        private String dn;

        public String getDn() {
            return dn;
        }

        public void setDn(String dn) {
            this.dn = dn;
        }
    }

    public static class CityBean {
        /**
         * attributes : {"cityX":"492","cityY":"100","cityname":"庆云县","centername":"庆云县","fontColor":"FFFFFF","pyName":"","state1":"21","state2":"21","stateDetailed":"小到中雨","tem1":"17","tem2":"21","temNow":"17","windState":"东北风微风级","windDir":"南风","windPower":"1级","humidity":"94%","time":"19:00","url":"101120407"}
         */

        private AttributesBeanX attributes;

        public AttributesBeanX getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBeanX attributes) {
            this.attributes = attributes;
        }

        public static class AttributesBeanX {
            /**
             * cityX : 492
             * cityY : 100
             * cityname : 庆云县
             * centername : 庆云县
             * fontColor : FFFFFF
             * pyName :
             * state1 : 21
             * state2 : 21
             * stateDetailed : 小到中雨
             * tem1 : 17
             * tem2 : 21
             * temNow : 17
             * windState : 东北风微风级
             * windDir : 南风
             * windPower : 1级
             * humidity : 94%
             * time : 19:00
             * url : 101120407
             */

            private String cityX;
            private String cityY;
            private String cityname;
            private String centername;
            private String fontColor;
            private String pyName;
            private String state1;
            private String state2;
            private String stateDetailed;
            private String tem1;
            private String tem2;
            private String temNow;
            private String windState;
            private String windDir;
            private String windPower;
            private String humidity;
            private String time;
            private String url;

            public String getCityX() {
                return cityX;
            }

            public void setCityX(String cityX) {
                this.cityX = cityX;
            }

            public String getCityY() {
                return cityY;
            }

            public void setCityY(String cityY) {
                this.cityY = cityY;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getCentername() {
                return centername;
            }

            public void setCentername(String centername) {
                this.centername = centername;
            }

            public String getFontColor() {
                return fontColor;
            }

            public void setFontColor(String fontColor) {
                this.fontColor = fontColor;
            }

            public String getPyName() {
                return pyName;
            }

            public void setPyName(String pyName) {
                this.pyName = pyName;
            }

            public String getState1() {
                return state1;
            }

            public void setState1(String state1) {
                this.state1 = state1;
            }

            public String getState2() {
                return state2;
            }

            public void setState2(String state2) {
                this.state2 = state2;
            }

            public String getStateDetailed() {
                return stateDetailed;
            }

            public void setStateDetailed(String stateDetailed) {
                this.stateDetailed = stateDetailed;
            }

            public String getTem1() {
                return tem1;
            }

            public void setTem1(String tem1) {
                this.tem1 = tem1;
            }

            public String getTem2() {
                return tem2;
            }

            public void setTem2(String tem2) {
                this.tem2 = tem2;
            }

            public String getTemNow() {
                return temNow;
            }

            public void setTemNow(String temNow) {
                this.temNow = temNow;
            }

            public String getWindState() {
                return windState;
            }

            public void setWindState(String windState) {
                this.windState = windState;
            }

            public String getWindDir() {
                return windDir;
            }

            public void setWindDir(String windDir) {
                this.windDir = windDir;
            }

            public String getWindPower() {
                return windPower;
            }

            public void setWindPower(String windPower) {
                this.windPower = windPower;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
