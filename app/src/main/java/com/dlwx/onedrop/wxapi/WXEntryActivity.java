package com.dlwx.onedrop.wxapi;

import android.content.Intent;
import android.util.Log;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2017/6/20.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    //微信appSecret
    private String APP_SECRET = "852e7e62be2e15f5d07fb70ad440cd59";
    private String AppID = "wxd9d9719bda9d3dc0";
    //第三方app和微信通信的openapid接口
    // 这两个参数在文档中没有找到，可能是瞎了,,,自己在代码里面找了会才找到，这两个常量代表了微信返回的消息类型，是对登录的处理还是对分享的处理，登录会在后面介绍到
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    private String unionid;


    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(this, AppID, true);
        api.handleIntent(this.getIntent(), this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(intent, this);
        setIntent(intent);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("req", baseReq.toString());
        this.finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        wch(errCode);
        if (errCode == 0) {
            int type = resp.getType();
            wch(type);
            if (resp.getType() == RETURN_MSG_TYPE_SHARE) {
                if (CallBackListenerUtiles.callBackListener != null) {
                    CallBackListenerUtiles.callBackListener.setComplete();
                }
            }else{
                SendAuth.Resp newResp = (SendAuth.Resp) resp;
                //获取微信传回的code
                String code = newResp.code;
                wch("code:"+code);
                getAccess_token(code);
            }
            finish();
        }
        switch (resp.errCode) {
            //发送成功
            case BaseResp.ErrCode.ERR_OK:

                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                   String code = sendResp.code;
                }else{
                   
                }

                finish();

                break;
            //发送取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                AllInterface.wxLoginListen.loginListener(false);
                break;
            //发送拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                finish();
                AllInterface.wxLoginListen.loginListener(false);
                break;


            default:
                //发送返回
                finish();
                AllInterface.wxLoginListen.loginListener(false);
                loading.dismiss();
                break;
        }


    }

    /**
     * 获取openid  accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(String code) {

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + AppID
                + "&secret="
                + APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        OkGo.<String>get(path)
               .execute(new StringCallback() {
                   @Override
                   public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                       try {
                           JSONObject object = new JSONObject(response.body());
                           //获取用户的openid
                           String openid = object.getString("openid");
                           String access_token = object.getString("access_token");

                           getUserMsg(access_token, openid);
                       } catch (JSONException e) {
                           wch("try："+e);
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onError(Response<String> response) {
                       wch("错误："+response.body());
                       super.onError(response);
                   }
               });
    }
    /**
     * 获取微信个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMsg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        OkGo.<String>get(path)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        WXLoginBean wxLoginBean = gson.fromJson(response.body(), WXLoginBean.class);
                        String nickName = wxLoginBean.getNickname();
                        String picUrl = wxLoginBean.getHeadimgurl();
                        String openid = wxLoginBean.getOpenid();
                        String unionid = wxLoginBean.getUnionid();
                        int sex = wxLoginBean.getSex();
                        String gender;
                        if (sex == 0) {
                            gender = "男";
                        }else{
                            gender = "女";
                        }
                        WXEntryActivity.this.unionid = wxLoginBean.getUnionid();
                        Log.d("wch", nickName + ";;;unionid:" + unionid + ";;;" + picUrl + ":" + WXEntryActivity.this.unionid);
                        if (CallBackListenerUtiles.callBackListener != null) {
                            CallBackListenerUtiles.callBackListener.getUserInfo(nickName,picUrl,unionid,gender);
                        }
                    }
                });
    }

}
