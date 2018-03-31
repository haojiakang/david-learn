package com.david.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.logging.log4j.util.Strings;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jiakang on 2017/12/24.
 */
@Slf4j
public class JieLongTest {

    private HttpClient client = new HttpClient();
    private String domain = "http://127.0.0.1:5000";

    @Before
    public void init() {
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
    }

    @Test
    public void test() throws IOException {
        onListen();
    }

    private void onListen() {
        long loginUserUid = getLoginUserUid();
        String previous = "";
        while (true) {
            try {
                String onListenMessageUrl = domain + "/openqq/check_event";
                String revMsg = httpGet(onListenMessageUrl);
                JSONArray array = JSONObject.parseArray(revMsg);
                if (array.size() < 1) {
                    continue;
                }
                String msg = array.getString(0);
                JSONObject msgJson = JSONObject.parseObject(msg);
                if (msgJson == null) {
                    continue;
                }
                String receiveMsg = msgJson.getString("content");
                long senderUid = msgJson.getLong("sender_uid");
                if (senderUid == loginUserUid) {
                    String huifuMsg = onReceiveMsg(receiveMsg);
                    log.info("tips:{}", huifuMsg);
                    continue;
                }
                log.info("receive msg:{}", receiveMsg);
                boolean passCheck = false;
                if (Strings.isEmpty(previous)) {
                    passCheck = true;
                } else {
                    String lastWord = previous.substring(previous.length() - 1);
                    if (receiveMsg.startsWith(lastWord)) {
                        passCheck = true;
                    }
                }
                String huifuMsg;
                if (passCheck) {
                    huifuMsg = onReceiveMsg(receiveMsg);
                    if (Strings.isEmpty(huifuMsg)) {
                        huifuMsg = "哎呀，被你难住了";
                    } else {
                        previous = huifuMsg;
                    }
                } else {
                    huifuMsg = "你丫逗我呢？再耍赖不陪你玩了啊！";
                }
                sendHuifuMsg(senderUid, huifuMsg);
                log.info("receiveMsg:{}, huifuMsg:{}", receiveMsg, huifuMsg);
            } catch (Exception e) {
                log.info("onListen error", e);
            }

        }
    }

    @Test
    public void testGetLoginUserUid() {
        long loginUserUid = getLoginUserUid();
        System.out.println(loginUserUid);
    }

    private long getLoginUserUid() {
        String userInfoUrl = domain + "/openqq/get_user_info";
        String userInfo = httpGet(userInfoUrl);
        return JSONObject.parseObject(userInfo).getLong("account");
    }

    @Test
    public void onReceiveMsgTest() {
        String receiveMsg = "你妹你妹";
        onReceiveMsg(receiveMsg);
    }

    private String onReceiveMsg(String receiveMsg) {
        int length = receiveMsg.length();
        String huifuMsg;
        if (length < 4) {
            log.info("receiveMsg is not chengyu, so ignore. receiveMsg:{}, length:{}", receiveMsg, length);
            huifuMsg = "你说的不是成语哦！";
        } else {
            String lastWord = receiveMsg.substring(length - 1);
            String jielongUrl = "https://chengyujielong.51240.com/{}__chengyujielong/";
            String finalJieLongUrl = null;
            try {
                finalJieLongUrl = jielongUrl.replace("{}", URLEncoder.encode(lastWord, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String rawHuifu = httpGet(finalJieLongUrl);
            huifuMsg = parseHtml(rawHuifu);
//            if (Strings.isEmpty(huifuMsg)) {
//                huifuMsg = "哎呀，被你难住了！";
//            }
        }
        return huifuMsg;
    }

    @Test
    public void testHuifuMsg() {
        long toUid = 564545537L;
        String huifuMsg = "顺风使船";
        String result = sendHuifuMsg(toUid, huifuMsg);
        System.out.println(result);
    }

    private String sendHuifuMsg(long toUid, String huifuMsg) {
        String huifuMessageUrl = domain + "/openqq/send_friend_message?uid=${uid}&content=${content}";
        try {
            huifuMsg = URLEncoder.encode(huifuMsg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String finalUrl = huifuMessageUrl.replace("${uid}", Long.toString(toUid)).replace("${content}", huifuMsg);
        String result = httpGet(finalUrl);
        return result;
    }

    private String httpGet(String url) {
        HttpMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void testParseHtml() {
        String html = "\n" +
                "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-transform\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0\" />\n" +
                "<meta name=\"applicable-device\" content=\"pc,mobile\" />\n" +
                "<link rel=\"apple-touch-icon-precomposed\" sizes=\"57x57\" href=\"//f.51240.com/file/chengyujielong/i_c_o_57x57.png\" />\n" +
                "<link rel=\"apple-touch-icon-precomposed\" sizes=\"72x72\" href=\"//f.51240.com/file/chengyujielong/i_c_o_72x72.png\" />\n" +
                "<link rel=\"apple-touch-icon-precomposed\" sizes=\"114x114\" href=\"//f.51240.com/file/chengyujielong/i_c_o_114x114.png\" />\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\" />\n" +
                "<meta name=\"keywords\" content=\"顺,成语接龙,成语接龙游戏,在线成语接龙,四字成语接龙\" />\n" +
                "<meta name=\"description\" content=\"顺 - 在线成语接龙游戏\" />\n" +
                "<title>《顺》成语接龙 - 成语接龙游戏 - 在线成语接龙 - 四字成语接龙 - 智能成语接龙</title>\n" +
                "<script>cache_sjs=\"17052210\";sj_jie_mian=\"1\";</script>\n" +
                "<link href=\"//f.51240.com/img/css/style.css?v=17052210\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<script src=\"//f.51240.com/img/js/js.js?v=17052210\"></script>\n" +
                "<script src=\"//f.51240.com/g/data/data.js?v=17052210\"></script>\n" +
                "<script>all_zhi_xing_js_head();</script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div id=\"pmk_sj_top\" class=\"pmk_sj_show\">\n" +
                "<a href=\"//www.51240.com/\" id=\"pmk_sj_top_home\">　</a>\n" +
                "<span>成语接龙</span>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div id=\"top\" class=\"pmk_990_show pmk_1040_show\">\n" +
                "\n" +
                "<div style=\"float:left;\">\n" +
                "<div id=\"fenxiang_div\" style=\"float:left;\">分享功能载入中..</div>\n" +
                "</div>\n" +
                "\n" +
                "<div style=\"float:right;\">\n" +
                "<a href=\"#\" onclick=\"this.style.behavior='url(#default#homepage)';this.setHomePage(document.location.href);\">设为首页</a>\n" +
                "<a href=\"#\" onclick=\"javascript:addfavorite();\">加入收藏</a>\n" +
                "<a style=\"color:#363;font-weight:bold;\" href=\"//www.51240.com/api/\" target=\"_blank\">接口调用</a>\n" +
                "<a style=\"color:#F00;\" href=\"//about.7x24s.com/contact/\" target=\"_blank\" rel=\"nofollow\">意见/报错</a>\n" +
                "</div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div id=\"main\">\n" +
                "\n" +
                "\n" +
                "<div id=\"top_logo\" class=\"pmk_990_show pmk_1040_show\">\n" +
                "<a href=\"//www.51240.com/\"><img id=\"top_logo_img\" src=\"//f.51240.com/img/logo.gif?v=17052210\" alt=\"便民查询网 - 51240.com - 在线查询 - 实用工具\" /></a>\n" +
                "<div id=\"ggwz___1\" class=\"pmk_990_show pmk_1040_show\"><script>ggdm_duqu(\"ggwz___1\", \"468x60\");</script></div>\n" +
                "</div>\n" +
                "\n" +
                "<script>try{parent.dingbu_shezhi();}catch (e){}</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!-- 左侧开始 -->\n" +
                "<div id=\"main_left\">\n" +
                "\n" +
                "<div class=\"kuang\" style=\"margin-bottom: 8px;\">\n" +
                "\n" +
                "\n" +
                "<div id=\"main_title\" class=\"kuang_title\" >\n" +
                "<div id=\"main_shouye_anniu\" style=\"float: left;\"><a href=\"//www.51240.com/\">首页</a><span class=\"main_title_gt\">&gt;</span></div>\n" +
                "<div style=\"float:left;\"><h1><a href=\"//chengyujielong.51240.com/\" id=\"main_anniu\">成语接龙</a></h1></div>\n" +
                "\n" +
                "<div id=\"ggwz___2\" class=\"pmk_990_show pmk_1040_show\" style=\"width:468px;height:15px;float:right;\"><script>ggdm_duqu(\"ggwz___2\", \"468x15\");</script></div><div id=\"main_qian_jin_hou_tui\" style=\"float:right;\"></div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<div id=\"main_content\">\n" +
                "\n" +
                "\n" +
                "<div class=\"nry_bt\">\n" +
                "<img src=\"//f.51240.com/file/chengyujielong/i_c_o.png?v=17052210\" alt=\"成语接龙\">\n" +
                "<div class=\"nry_bt_text\">成语接龙</div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"huisefengexian\"></div>\n" +
                "\n" +
                "<script>\n" +
                "//提交搜索\n" +
                "function shouyetijiao(){\n" +
                "window.location.href=\"/\"+encodeURI(''+document.getElementById(\"gjz_wenzi\").value+'')+\"__chengyujielong/\";\n" +
                "}\n" +
                "</script>\n" +
                "\n" +
                "<div align=\"center\">\n" +
                "\n" +
                "成语：\n" +
                "<input type=\"text\" id=\"gjz_wenzi\" size=\"15\" value=\"顺\" onBlur=\"this.className='all_srk_1';\" onFocus=\"this.className='all_srk_2';\" class=\"all_srk_1\" />\n" +
                "<input type=\"submit\" onclick=\"shouyetijiao();\" value=\"接龙\" class=\"all_an_1\" />\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<h2 class=\"xiaoshuomingkuang_biaoti\">“顺”字在开头的成语</h2>\n" +
                "<ul class=\"list_2\"><li><a href=\"//chengyu.51240.com/shunfengshichuan__chengyuchaxun/\" target=\"_blank\">顺风使船</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E4%BD%BF%E8%88%B9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“船”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunrenyingtian__chengyuchaxun/\" target=\"_blank\">顺人应天</a> <a href=\"/%E9%A1%BA%E4%BA%BA%E5%BA%94%E5%A4%A9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“天”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunmanmogua__chengyuchaxun/\" target=\"_blank\">顺蔓摸瓜</a> <a href=\"/%E9%A1%BA%E8%94%93%E6%91%B8%E7%93%9C__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“瓜”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunkoukaihe__chengyuchaxun/\" target=\"_blank\">顺口开河</a> <a href=\"/%E9%A1%BA%E5%8F%A3%E5%BC%80%E6%B2%B3__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“河”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunguoshifei__chengyuchaxun/\" target=\"_blank\">顺过饰非</a> <a href=\"/%E9%A1%BA%E8%BF%87%E9%A5%B0%E9%9D%9E__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“非”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengzhangfan__chengyuchaxun/\" target=\"_blank\">顺风张帆</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E5%BC%A0%E5%B8%86__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“帆”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengxingchuan__chengyuchaxun/\" target=\"_blank\">顺风行船</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E8%A1%8C%E8%88%B9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“船”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengshifan__chengyuchaxun/\" target=\"_blank\">顺风使帆</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E4%BD%BF%E5%B8%86__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“帆”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengshiduo__chengyuchaxun/\" target=\"_blank\">顺风使舵</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E4%BD%BF%E8%88%B5__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“舵”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengchefan__chengyuchaxun/\" target=\"_blank\">顺风扯帆</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E6%89%AF%E5%B8%86__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“帆”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfeierze__chengyuchaxun/\" target=\"_blank\">顺非而泽</a> <a href=\"/%E9%A1%BA%E9%9D%9E%E8%80%8C%E6%B3%BD__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“泽”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntianyingren__chengyuchaxun/\" target=\"_blank\">顺天应人</a> <a href=\"/%E9%A1%BA%E5%A4%A9%E5%BA%94%E4%BA%BA__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“人”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuirenqing__chengyuchaxun/\" target=\"_blank\">顺水人情</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E4%BA%BA%E6%83%85__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“情”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshierdong__chengyuchaxun/\" target=\"_blank\">顺时而动</a> <a href=\"/%E9%A1%BA%E6%97%B6%E8%80%8C%E5%8A%A8__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“动”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunmeikuange__chengyuchaxun/\" target=\"_blank\">顺美匡恶</a> <a href=\"/%E9%A1%BA%E7%BE%8E%E5%8C%A1%E6%81%B6__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“恶”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengzhuanduo__chengyuchaxun/\" target=\"_blank\">顺风转舵</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E8%BD%AC%E8%88%B5__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“舵”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengshichuan_7hm__chengyuchaxun/\" target=\"_blank\">顺风驶船</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E9%A9%B6%E8%88%B9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“船”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengchuihuo__chengyuchaxun/\" target=\"_blank\">顺风吹火</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E5%90%B9%E7%81%AB__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“火”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunfengerhu__chengyuchaxun/\" target=\"_blank\">顺风而呼</a> <a href=\"/%E9%A1%BA%E9%A3%8E%E8%80%8C%E5%91%BC__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“呼”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunlichengzhang__chengyuchaxun/\" target=\"_blank\">顺理成章</a> <a href=\"/%E9%A1%BA%E7%90%86%E6%88%90%E7%AB%A0__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“章”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshouqianyang__chengyuchaxun/\" target=\"_blank\">顺手牵羊</a> <a href=\"/%E9%A1%BA%E6%89%8B%E7%89%B5%E7%BE%8A__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“羊”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuituizhou__chengyuchaxun/\" target=\"_blank\">顺水推舟</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E6%8E%A8%E8%88%9F__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“舟”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntengmogua__chengyuchaxun/\" target=\"_blank\">顺藤摸瓜</a> <a href=\"/%E9%A1%BA%E8%97%A4%E6%91%B8%E7%93%9C__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“瓜”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunzhizhexing_nizhizhewang__chengyuchaxun/\" target=\"_blank\">顺之者兴，逆之者亡</a> <a href=\"/%E9%A1%BA%E4%B9%8B%E8%80%85%E5%85%B4%EF%BC%8C%E9%80%86%E4%B9%8B%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunzhizhechang_nizhizhewang__chengyuchaxun/\" target=\"_blank\">顺之者昌，逆之者亡</a> <a href=\"/%E9%A1%BA%E4%B9%8B%E8%80%85%E6%98%8C%EF%BC%8C%E9%80%86%E4%B9%8B%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunwozhesheng_niwozhesi__chengyuchaxun/\" target=\"_blank\">顺我者生，逆我者死</a> <a href=\"/%E9%A1%BA%E6%88%91%E8%80%85%E7%94%9F%EF%BC%8C%E9%80%86%E6%88%91%E8%80%85%E6%AD%BB__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“死”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntianzhecun_nitianzhewang__chengyuchaxun/\" target=\"_blank\">顺天者存，逆天者亡</a> <a href=\"/%E9%A1%BA%E5%A4%A9%E8%80%85%E5%AD%98%EF%BC%8C%E9%80%86%E5%A4%A9%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntianzhechang_nitianzhewang__chengyuchaxun/\" target=\"_blank\">顺天者昌，逆天者亡</a> <a href=\"/%E9%A1%BA%E5%A4%A9%E8%80%85%E6%98%8C%EF%BC%8C%E9%80%86%E5%A4%A9%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shundezhechang_nidezhewang__chengyuchaxun/\" target=\"_blank\">顺德者昌，逆德者亡</a> <a href=\"/%E9%A1%BA%E5%BE%B7%E8%80%85%E6%98%8C%EF%BC%8C%E9%80%86%E5%BE%B7%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shundaozhechang_nidezhewang__chengyuchaxun/\" target=\"_blank\">顺道者昌，逆德者亡</a> <a href=\"/%E9%A1%BA%E9%81%93%E8%80%85%E6%98%8C%EF%BC%8C%E9%80%86%E5%BE%B7%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunwozhechang_niwozhewang__chengyuchaxun/\" target=\"_blank\">顺我者昌，逆我者亡</a> <a href=\"/%E9%A1%BA%E6%88%91%E8%80%85%E6%98%8C%EF%BC%8C%E9%80%86%E6%88%91%E8%80%85%E4%BA%A1__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“亡”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntianyingshi__chengyuchaxun/\" target=\"_blank\">顺天应时</a> <a href=\"/%E9%A1%BA%E5%A4%A9%E5%BA%94%E6%97%B6__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“时”字接龙</a></li><li><a href=\"//chengyu.51240.com/shuntiancongren__chengyuchaxun/\" target=\"_blank\">顺天从人</a> <a href=\"/%E9%A1%BA%E5%A4%A9%E4%BB%8E%E4%BA%BA__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“人”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuixingzhou__chengyuchaxun/\" target=\"_blank\">顺水行舟</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E8%A1%8C%E8%88%9F__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“舟”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuituichuan__chengyuchaxun/\" target=\"_blank\">顺水推船</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E6%8E%A8%E8%88%B9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“船”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuishunfeng__chengyuchaxun/\" target=\"_blank\">顺水顺风</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E9%A1%BA%E9%A3%8E__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“风”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshuifangchuan__chengyuchaxun/\" target=\"_blank\">顺水放船</a> <a href=\"/%E9%A1%BA%E6%B0%B4%E6%94%BE%E8%88%B9__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“船”字接龙</a></li><li><a href=\"//chengyu.51240.com/shunshisuisu__chengyuchaxun/\" target=\"_blank\">顺时随俗</a> <a href=\"/%E9%A1%BA%E6%97%B6%E9%9A%8F%E4%BF%97__chengyujielong/\" style=\"color:#F00;\" title=\"以这个成语进行接龙\">以“俗”字接龙</a></li></ul>\n" +
                "<br /><br /><br />\n" +
                "\n" +
                "</div>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"ggwz___3\" class=\"kuang pmk_990_show pmk_1040_show\" style=\"height:90px;margin-bottom:8px;\"><script>ggdm_duqu(\"ggwz___3\", \"728x90\");</script></div>\n" +
                "\n" +
                "</div>\n" +
                "<!-- 左侧结束 -->\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!-- 右侧开始 -->\n" +
                "<div id=\"main_right\" class=\"pmk_990_show pmk_1040_show\">\n" +
                "<div id=\"main_right_zi\">\n" +
                "\n" +
                "<div id=\"ggwz___4\" class=\"pmk_990_show\" style=\"background-color:#DDF4FE;height:250px;margin-bottom:8px;\"><script>ggdm_duqu(\"ggwz___4\", \"250x250\");</script></div>\n" +
                "<div id=\"ggwz___5\" class=\"pmk_1040_show\" style=\"background-color:#DDF4FE;height:250px;margin-bottom:8px;\"><script>ggdm_duqu(\"ggwz___5\", \"300x250\");</script></div>\n" +
                "\n" +
                "\n" +
                "<div class=\"kuang\" style=\"margin-bottom:8px;\">\n" +
                "<div class=\"kuang_title\">推荐工具</div>\n" +
                "<ul class=\"tl\">\n" +
                "<li><a href=\"//shouji.51240.com/\" target=\"_blank\">手机号码查询</a></li><li><a href=\"//tel.51240.com/\" target=\"_blank\">固定电话查询</a></li><li><a href=\"//shenfenzheng.51240.com/\" target=\"_blank\">身份证查询</a></li><li><a href=\"//weixingditu.51240.com/\" target=\"_blank\">卫星地图</a></li><li><a href=\"//fanyi.51240.com/\" target=\"_blank\">在线翻译</a></li><li><a href=\"//qqjiazhi.51240.com/\" target=\"_blank\">QQ价值评估</a></li><li><a href=\"//ip.51240.com/\" target=\"_blank\">IP地址查询</a></li><li><a href=\"//laohuangli.51240.com/\" target=\"_blank\">老黄历</a></li><li><a href=\"//naozhong.51240.com/\" target=\"_blank\">在线闹钟</a></li><li><a href=\"//huobiduihuan.51240.com/\" target=\"_blank\">货币汇率兑换</a></li></ul>\n" +
                "<div></div>\n" +
                "</div>\n" +
                "\n" +
                "<div></div>\n" +
                "<div id=\"ggwz___6\" class=\"pmk_990_show\" style=\"background-color:#DDF4FE;height:250px;margin-bottom:8px;\"><script>ggdm_duqu(\"ggwz___6\", \"250x250\");</script></div>\n" +
                "<div id=\"ggwz___7\" class=\"pmk_1040_show\" style=\"background-color:#DDF4FE;height:250px;margin-bottom:8px;\"><script>ggdm_duqu(\"ggwz___7\", \"300x250\");</script></div>\n" +
                "<div></div>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "</div>\n" +
                "<!-- 右侧结束 -->\n" +
                "\n" +
                "\n" +
                "<div class=\"pmk_990_show pmk_1040_show\">\n" +
                "<div id=\"ggwz___8\" class=\"pmk_990_show\" style=\"height:52px;clear:both;\"><script>ggdm_duqu(\"ggwz___8\", \"990x52\");</script></div>\n" +
                "<div id=\"ggwz___9\" class=\"pmk_1040_show\" style=\"height:52px;clear:both;\"><script>ggdm_duqu(\"ggwz___9\", \"1040x52\");</script></div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div id=\"ggwz___10\" class=\"pmk_sj_show\" style=\"margin-top:4px;clear:both;\"><script>ggdm_duqu(\"ggwz___10\", \"20bi20\");</script></div>\n" +
                "<div id=\"ggwz___11\" class=\"pmk_sj_show\"><script>ggdm_duqu(\"ggwz___11\", \"sj_cp\");</script></div>\n" +
                "<div id=\"ggwz___12\" class=\"pmk_sj_show\"><script>ggdm_duqu(\"ggwz___12\", \"wangyeji\");</script></div>\n" +
                "\n" +
                "<div id=\"ggwz___13\" class=\"pmk_990_show pmk_1040_show pmk_api_show\"><script>ggdm_duqu(\"ggwz___13\", \"pc_tujia\");</script></div>\n" +
                "<div id=\"ggwz___14\" class=\"pmk_sj_show\"><script>ggdm_duqu(\"ggwz___14\", \"sj_tujia\");</script></div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script type=\"text/javascript\">try{parent.zhongbu_shezhi();}catch (e){}</script>\n" +
                "<script type=\"text/javascript\">lazyLoad.init();</script>\n" +
                "\n" +
                "<script>all_zhi_xing_js();</script>\n" +
                "\n" +
                "\n" +
                "<div id=\"bottom_fjxx\">\n" +
                "<div class=\"kuang pmk_990_show pmk_1040_show\" style=\"margin-top:8px;\"><div class=\"kuang_title\">随机推荐查询工具</div><div style=\"padding: 5px;line-height: 18px;\"><a href=\"//ftp.51240.com/\" target=\"_blank\">在线FTP登录</a> <a href=\"//javascript.51240.com/\" target=\"_blank\">JavaScript / Html / Css 格式化</a> <a href=\"//bihua.51240.com/\" target=\"_blank\">汉字笔画查询</a> <a href=\"//zhoupu.51240.com/\" target=\"_blank\">粥谱大全</a> <a href=\"//jiufang.51240.com/\" target=\"_blank\">酒方大全</a> <a href=\"//shengjing.51240.com/\" target=\"_blank\">在线圣经</a> <a href=\"//process.51240.com/\" target=\"_blank\">进程查询</a> <a href=\"//raokouling.51240.com/\" target=\"_blank\">绕口令大全</a> <a href=\"//foxue.51240.com/\" target=\"_blank\">佛学大辞典</a> <a href=\"//chepai.51240.com/\" target=\"_blank\">车牌查询</a> <a href=\"//jiaotongbiaozhi.51240.com/\" target=\"_blank\">交通标志</a> <a href=\"//email.51240.com/\" target=\"_blank\">邮箱图标</a> <a href=\"//jietu.51240.com/\" target=\"_blank\">在线网页截图</a> <a href=\"//hanyingcidian.51240.com/\" target=\"_blank\">汉英词典</a> <a href=\"//qqpinyin.51240.com/\" target=\"_blank\">在线云拼音输入法</a> <a href=\"//cangjieshurufa.51240.com/\" target=\"_blank\">在线仓颉输入法</a> <a href=\"//mingyan.51240.com/\" target=\"_blank\">名人名言大全</a> <a href=\"//idn.51240.com/\" target=\"_blank\">多语种域名在线转码</a> <a href=\"//swgwsm.51240.com/\" target=\"_blank\">新编十万个为什么</a> <a href=\"//zuci.51240.com/\" target=\"_blank\">在线组词</a> <a href=\"//iq.51240.com/\" target=\"_blank\">IQ测试</a> <a href=\"//gonglinongli.51240.com/\" target=\"_blank\">公历农历转换</a> <a href=\"//nvyoujiage.51240.com/\" target=\"_blank\">女友价格计算</a> <a href=\"//nanyoujiage.51240.com/\" target=\"_blank\">男友价格计算</a> <a href=\"//jieqi.51240.com/\" target=\"_blank\">二十四节气查询</a> <a href=\"//httphead.51240.com/\" target=\"_blank\">HTTP头检测</a> <a href=\"//zaixianwubishuru98.51240.com/\" target=\"_blank\">在线五笔输入法 98版</a> <a href=\"//erbishurufa.51240.com/\" target=\"_blank\">在线二笔输入法</a> <a href=\"//huawenpinkuaishurufa.51240.com/\" target=\"_blank\">在线华文拼块输入法</a> <a href=\"//zhengmashurufa.51240.com/\" target=\"_blank\">郑码输入法</a> <a href=\"//ziranmashurufa.51240.com/\" target=\"_blank\">在线自然码输入法</a> <a href=\"//ps.51240.com/\" target=\"_blank\">在线图像处理</a> <a href=\"//yingyang.51240.com/\" target=\"_blank\">食物营养成分</a> <a href=\"//unixtime.51240.com/\" target=\"_blank\">Unix时间戳转换</a> <a href=\"//time.51240.com/\" target=\"_blank\">世界时间</a> <a href=\"//bigtosmall.51240.com/\" target=\"_blank\">英文字母大小写转换</a> （<a href=\"//www.51240.com/\" target=\"_blank\">查看全部</a>）</div></div></div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"kuang pmk_sj_show\" id=\"pmk_sj_hddb\">欢迎关注微信公众号：<span class=\"lvse\">便民查询大全</span></div>\n" +
                "<div class=\"pmk_sj_show\" id=\"pmk_sj_lxwm\"><a href=\"//about.7x24s.com/contact/\" rel=\"nofollow\">联系我们</a></div>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div id=\"bottom_top\" class=\"pmk_990_show pmk_1040_show\">\n" +
                "<a href=\"//www.51240.com/api/\" target=\"_blank\">接口调用</a>\n" +
                "|\n" +
                "<a href=\"//about.7x24s.com/contact/\" target=\"_blank\" rel=\"nofollow\">意见建议</a>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<iframe src=\"//cj.7x24s.com/zgz/ping/n_u_p/\" style=\"display:none;\"></iframe>\n" +
                "<div id=\"bottom\" class=\"pmk_990_show pmk_1040_show\">\n" +
                "CopyRight &copy; 2004-2017 <a href=\"//www.51240.com/\">便民查询网</a>\n" +
                "All Rights Reserved<br /><a href=\"http://www.miitbeian.gov.cn/\" target=\"_blank\" rel=\"nofollow\" style=\"text-decoration:none;\">闽ICP备05000099号</a>\n" +
                "<div style=\"height:20px;line-height:20px;text-align:center;\"><a href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=35012202350127\" target=\"_blank\" rel=\"nofollow\" style=\"text-decoration:none;\"><img src=\"//f.7x24s.com/public/img/beian_gov_cn.png\" style=\"vertical-align:middle;margin-right:5px;\" />闽公网安备 35012202350127号</a></div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script>try{parent.dibu_shezhi();}catch (e){}</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script>jie_mian_qie_huan();</script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script>xuan_ting(\"main_left\", \"main_right_zi\");dui_lian_guang_gao();</script>\n" +
                "\n" +
                "<div style=\"display:none\"><script src=\"//f.51240.com/tongji/51240.com.js?v=17052210\"></script></div>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" +
                "<!-- 114.55.175.188 |-ok-| -->";
        String result = parseHtml(html);
        System.out.println(result);
    }

    private String parseHtml(String html) {
        String findStr = "字在开头的成语</h2>\n";
        if (html.contains(findStr)) {
            int pot = html.indexOf(findStr) + findStr.length();
            int endPot = html.indexOf("\n", pot);
            html = html.substring(pot, endPot);
            try {
                Document document = DocumentHelper.parseText(html);
                return ((DefaultText) ((DefaultElement) ((DefaultElement) document.getRootElement().content().get(0)).content().get(0)).content().get(0)).getText();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String httpPost(String url) {
        HttpMethod method = new PostMethod(url);
        try {
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void test1() {
        System.out.println("空房".length());
        System.out.println("空房".substring("空房".length() - 1));
    }
}
