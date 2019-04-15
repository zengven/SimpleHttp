package com.yexin.http;

import java.util.List;

/**
 * @author zengven
 * @ClassName: WeatherBean
 * @Description: TODO
 * @date 2017/5/9 16:40
 */

public class WeatherBean {

    public List<HeWeather5Bean> HeWeather5;

    @Override
    public String toString() {
        return "WeatherBean{" +
                "HeWeather5=" + HeWeather5 +
                '}';
    }

    public static class HeWeather5Bean {
        /**
         * aqi : {"city":{"aqi":"81","co":"0","no2":"13","o3":"172","pm10":"112","pm25":"49","qlty":"良","so2":"6"}}
         * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904989","lon":"116.405285","update":{"loc":"2017-05-09 15:51","utc":"2017-05-09 07:51"}}
         * daily_forecast : [{"astro":{"mr":"17:40","ms":"04:27","sr":"05:06","ss":"19:16"},"cond":{"code_d":"101","code_n":"300","txt_d":"多云","txt_n":"阵雨"},"date":"2017-05-09","hum":"44","pcpn":"0.0","pop":"0","pres":"1006","tmp":{"max":"29","min":"15"},"uv":"7","vis":"20","wind":{"deg":"152","dir":"南风","sc":"3-4","spd":"11"}},{"astro":{"mr":"18:37","ms":"04:58","sr":"05:05","ss":"19:17"},"cond":{"code_d":"104","code_n":"100","txt_d":"阴","txt_n":"晴"},"date":"2017-05-10","hum":"50","pcpn":"0.0","pop":"2","pres":"1006","tmp":{"max":"29","min":"15"},"uv":"7","vis":"20","wind":{"deg":"212","dir":"南风","sc":"微风","spd":"7"}},{"astro":{"mr":"19:34","ms":"05:31","sr":"05:04","ss":"19:18"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2017-05-11","hum":"25","pcpn":"0.0","pop":"4","pres":"1007","tmp":{"max":"32","min":"17"},"uv":"7","vis":"20","wind":{"deg":"306","dir":"北风","sc":"3-4","spd":"15"}}]
         * hourly_forecast : [{"cond":{"code":"100","txt":"晴"},"date":"2017-05-09 16:00","hum":"26","pop":"0","pres":"1004","tmp":"27","wind":{"deg":"168","dir":"东南风","sc":"3-4","spd":"22"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-05-09 19:00","hum":"39","pop":"0","pres":"1005","tmp":"26","wind":{"deg":"132","dir":"东南风","sc":"3-4","spd":"20"}},{"cond":{"code":"100","txt":"晴"},"date":"2017-05-09 22:00","hum":"55","pop":"0","pres":"1007","tmp":"24","wind":{"deg":"74","dir":"东北风","sc":"微风","spd":"10"}}]
         * now : {"cond":{"code":"101","txt":"多云"},"fl":"27","hum":"27","pcpn":"0","pres":"1005","tmp":"28","vis":"7","wind":{"deg":"190","dir":"西南风","sc":"3-4","spd":"17"}}
         * status : ok
         * suggestion : {"air":{"brf":"很差","txt":"气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。"},"comf":{"brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},"sport":{"brf":"较适宜","txt":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。"},"trav":{"brf":"适宜","txt":"天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
         */

        public AqiBean aqi;
        public BasicBean basic;
        public NowBean now;
        public String status;
        public SuggestionBean suggestion;
        public List<DailyForecastBean> daily_forecast;
        public List<HourlyForecastBean> hourly_forecast;

        @Override
        public String toString() {
            return "HeWeather5Bean{" +
                    "aqi=" + aqi +
                    ", basic=" + basic +
                    ", now=" + now +
                    ", status='" + status + '\'' +
                    ", suggestion=" + suggestion +
                    ", daily_forecast=" + daily_forecast +
                    ", hourly_forecast=" + hourly_forecast +
                    '}';
        }

        public static class AqiBean {
            /**
             * city : {"aqi":"81","co":"0","no2":"13","o3":"172","pm10":"112","pm25":"49","qlty":"良","so2":"6"}
             */

            public CityBean city;

            @Override
            public String toString() {
                return "AqiBean{" +
                        "city=" + city +
                        '}';
            }

            public static class CityBean {
                /**
                 * aqi : 81
                 * co : 0
                 * no2 : 13
                 * o3 : 172
                 * pm10 : 112
                 * pm25 : 49
                 * qlty : 良
                 * so2 : 6
                 */

                public String aqi;
                public String co;
                public String no2;
                public String o3;
                public String pm10;
                public String pm25;
                public String qlty;
                public String so2;

                @Override
                public String toString() {
                    return "CityBean{" +
                            "aqi='" + aqi + '\'' +
                            ", co='" + co + '\'' +
                            ", no2='" + no2 + '\'' +
                            ", o3='" + o3 + '\'' +
                            ", pm10='" + pm10 + '\'' +
                            ", pm25='" + pm25 + '\'' +
                            ", qlty='" + qlty + '\'' +
                            ", so2='" + so2 + '\'' +
                            '}';
                }
            }
        }

        public static class BasicBean {
            /**
             * city : 北京
             * cnty : 中国
             * id : CN101010100
             * lat : 39.904989
             * lon : 116.405285
             * update : {"loc":"2017-05-09 15:51","utc":"2017-05-09 07:51"}
             */

            public String city;
            public String cnty;
            public String id;
            public String lat;
            public String lon;
            public UpdateBean update;

            @Override
            public String toString() {
                return "BasicBean{" +
                        "city='" + city + '\'' +
                        ", cnty='" + cnty + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        ", update=" + update +
                        '}';
            }

            public static class UpdateBean {
                /**
                 * loc : 2017-05-09 15:51
                 * utc : 2017-05-09 07:51
                 */

                public String loc;
                public String utc;

                @Override
                public String toString() {
                    return "UpdateBean{" +
                            "loc='" + loc + '\'' +
                            ", utc='" + utc + '\'' +
                            '}';
                }
            }
        }

        public static class NowBean {
            /**
             * cond : {"code":"101","txt":"多云"}
             * fl : 27
             * hum : 27
             * pcpn : 0
             * pres : 1005
             * tmp : 28
             * vis : 7
             * wind : {"deg":"190","dir":"西南风","sc":"3-4","spd":"17"}
             */

            public CondBean cond;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            public WindBean wind;

            @Override
            public String toString() {
                return "NowBean{" +
                        "cond=" + cond +
                        ", fl='" + fl + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pcpn='" + pcpn + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp='" + tmp + '\'' +
                        ", vis='" + vis + '\'' +
                        ", wind=" + wind +
                        '}';
            }

            public static class CondBean {
                /**
                 * code : 101
                 * txt : 多云
                 */

                public String code;
                public String txt;

                @Override
                public String toString() {
                    return "CondBean{" +
                            "code='" + code + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class WindBean {
                /**
                 * deg : 190
                 * dir : 西南风
                 * sc : 3-4
                 * spd : 17
                 */

                public String deg;
                public String dir;
                public String sc;
                public String spd;

                @Override
                public String toString() {
                    return "WindBean{" +
                            "deg='" + deg + '\'' +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd='" + spd + '\'' +
                            '}';
                }
            }
        }

        public static class SuggestionBean {
            /**
             * air : {"brf":"很差","txt":"气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。"}
             * comf : {"brf":"较舒适","txt":"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。"}
             * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
             * drsg : {"brf":"热","txt":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"}
             * flu : {"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"}
             * sport : {"brf":"较适宜","txt":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。"}
             * trav : {"brf":"适宜","txt":"天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。"}
             * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
             */

            public AirBean air;
            public ComfBean comf;
            public CwBean cw;
            public DrsgBean drsg;
            public FluBean flu;
            public SportBean sport;
            public TravBean trav;
            public UvBean uv;

            @Override
            public String toString() {
                return "SuggestionBean{" +
                        "air=" + air +
                        ", comf=" + comf +
                        ", cw=" + cw +
                        ", drsg=" + drsg +
                        ", flu=" + flu +
                        ", sport=" + sport +
                        ", trav=" + trav +
                        ", uv=" + uv +
                        '}';
            }

            public static class AirBean {
                /**
                 * brf : 很差
                 * txt : 气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "AirBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class ComfBean {
                /**
                 * brf : 较舒适
                 * txt : 白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "ComfBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class CwBean {
                /**
                 * brf : 不宜
                 * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "CwBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class DrsgBean {
                /**
                 * brf : 热
                 * txt : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "DrsgBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class FluBean {
                /**
                 * brf : 少发
                 * txt : 各项气象条件适宜，无明显降温过程，发生感冒机率较低。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "FluBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class SportBean {
                /**
                 * brf : 较适宜
                 * txt : 天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "SportBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class TravBean {
                /**
                 * brf : 适宜
                 * txt : 天气较好，温度适宜，但风稍微有点大。这样的天气适宜旅游，您可以尽情地享受大自然的无限风光。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "TravBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class UvBean {
                /**
                 * brf : 弱
                 * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
                 */

                public String brf;
                public String txt;

                @Override
                public String toString() {
                    return "UvBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * astro : {"mr":"17:40","ms":"04:27","sr":"05:06","ss":"19:16"}
             * cond : {"code_d":"101","code_n":"300","txt_d":"多云","txt_n":"阵雨"}
             * date : 2017-05-09
             * hum : 44
             * pcpn : 0.0
             * pop : 0
             * pres : 1006
             * tmp : {"max":"29","min":"15"}
             * uv : 7
             * vis : 20
             * wind : {"deg":"152","dir":"南风","sc":"3-4","spd":"11"}
             */

            public AstroBean astro;
            public CondBeanX cond;
            public String date;
            public String hum;
            public String pcpn;
            public String pop;
            public String pres;
            public TmpBean tmp;
            public String uv;
            public String vis;
            public WindBeanX wind;

            @Override
            public String toString() {
                return "DailyForecastBean{" +
                        "astro=" + astro +
                        ", cond=" + cond +
                        ", date='" + date + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pcpn='" + pcpn + '\'' +
                        ", pop='" + pop + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp=" + tmp +
                        ", uv='" + uv + '\'' +
                        ", vis='" + vis + '\'' +
                        ", wind=" + wind +
                        '}';
            }

            public static class AstroBean {
                /**
                 * mr : 17:40
                 * ms : 04:27
                 * sr : 05:06
                 * ss : 19:16
                 */

                public String mr;
                public String ms;
                public String sr;
                public String ss;

                @Override
                public String toString() {
                    return "AstroBean{" +
                            "mr='" + mr + '\'' +
                            ", ms='" + ms + '\'' +
                            ", sr='" + sr + '\'' +
                            ", ss='" + ss + '\'' +
                            '}';
                }
            }

            public static class CondBeanX {
                /**
                 * code_d : 101
                 * code_n : 300
                 * txt_d : 多云
                 * txt_n : 阵雨
                 */

                public String code_d;
                public String code_n;
                public String txt_d;
                public String txt_n;

                @Override
                public String toString() {
                    return "CondBeanX{" +
                            "code_d='" + code_d + '\'' +
                            ", code_n='" + code_n + '\'' +
                            ", txt_d='" + txt_d + '\'' +
                            ", txt_n='" + txt_n + '\'' +
                            '}';
                }
            }

            public static class TmpBean {
                /**
                 * max : 29
                 * min : 15
                 */

                public String max;
                public String min;

                @Override
                public String toString() {
                    return "TmpBean{" +
                            "max='" + max + '\'' +
                            ", min='" + min + '\'' +
                            '}';
                }
            }

            public static class WindBeanX {
                /**
                 * deg : 152
                 * dir : 南风
                 * sc : 3-4
                 * spd : 11
                 */

                public String deg;
                public String dir;
                public String sc;
                public String spd;

                @Override
                public String toString() {
                    return "WindBeanX{" +
                            "deg='" + deg + '\'' +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd='" + spd + '\'' +
                            '}';
                }
            }
        }

        public static class HourlyForecastBean {
            /**
             * cond : {"code":"100","txt":"晴"}
             * date : 2017-05-09 16:00
             * hum : 26
             * pop : 0
             * pres : 1004
             * tmp : 27
             * wind : {"deg":"168","dir":"东南风","sc":"3-4","spd":"22"}
             */

            public CondBeanXX cond;
            public String date;
            public String hum;
            public String pop;
            public String pres;
            public String tmp;
            public WindBeanXX wind;

            @Override
            public String toString() {
                return "HourlyForecastBean{" +
                        "cond=" + cond +
                        ", date='" + date + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pop='" + pop + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp='" + tmp + '\'' +
                        ", wind=" + wind +
                        '}';
            }

            public static class CondBeanXX {
                /**
                 * code : 100
                 * txt : 晴
                 */

                public String code;
                public String txt;

                @Override
                public String toString() {
                    return "CondBeanXX{" +
                            "code='" + code + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class WindBeanXX {
                /**
                 * deg : 168
                 * dir : 东南风
                 * sc : 3-4
                 * spd : 22
                 */

                public String deg;
                public String dir;
                public String sc;
                public String spd;

                @Override
                public String toString() {
                    return "WindBeanXX{" +
                            "deg='" + deg + '\'' +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd='" + spd + '\'' +
                            '}';
                }
            }
        }
    }
}
