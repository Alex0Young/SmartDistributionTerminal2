/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.chatuidemo.ui;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.exceptions.HyphenateException;
import com.mob.MobSDK;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * register screen
 * 
 */
public class RegisterActivity extends BaseActivity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;
	private EditText confirmCode;
	private String phone;
	public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_register);
		userNameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		confirmCode = (EditText) findViewById(R.id.insert_code);

		findViewById(R.id.confirm_code).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				phone = userNameEditText.getText().toString().trim();

				//获取验证码
				if (TextUtils.isEmpty(phone))
					Toast.makeText(RegisterActivity.this,"号码不能为空",Toast.LENGTH_SHORT).show();

				Log.i("1234",phone.toString());
				SMSSDK.getVerificationCode("86",phone);
			}
		});

		EventHandler handler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {

				if (result == SMSSDK.RESULT_COMPLETE) {
					//回调完成
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						//提交验证码成功


						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
							}
						});
						return ;

					} else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(RegisterActivity.this, "语音验证发送", Toast.LENGTH_SHORT).show();
							}
						});
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						//获取验证码成功
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
							}
						});
					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
						Log.i("test", "test");

					}else if(event == SMSSDK.RESULT_ERROR){
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
							}
						});
					}
				} else {
					((Throwable) data).printStackTrace();
					Throwable throwable = (Throwable) data;
					throwable.printStackTrace();
					Log.i("1234", throwable.toString());
					try {
						JSONObject obj = new JSONObject(throwable.getMessage());
						final String des = obj.optString("detail");
						if (!TextUtils.isEmpty(des)) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
								}
							});
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};

		SMSSDK.registerEventHandler(handler);
	}

	public static boolean isPhoneNumber(String patternStr,CharSequence input){
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);

		if(matcher.find()){
			return true;
		}
		else{
			return false;
		}
	}

	public void register(View view) {
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		final String confirm_code = confirmCode.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_code)) {
			Toast.makeText(this, getResources().getString(R.string.Confirm_code_cannot_be_empty), Toast.LENGTH_SHORT).show();
			confirmCode.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
			return;
		}else if(!isPhoneNumber(PHONE_PATTERN,username)){
			Toast.makeText(this, getResources().getString(R.string.not_phone_number), Toast.LENGTH_SHORT).show();
			return;
		}
		SMSSDK.submitVerificationCode("86",phone,confirm_code);

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage(getResources().getString(R.string.Is_the_registered));
			pd.show();

			new Thread(new Runnable() {
				public void run() {
					try {
						// call method in SDK
						EMClient.getInstance().createAccount(username, pwd);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								// save current user
								DemoHelper.getInstance().setCurrentUserName(username);
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), Toast.LENGTH_SHORT).show();
								finish();
							}
						});
					} catch (final HyphenateException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode=e.getErrorCode();
								if(errorCode==EMError.NETWORK_ERROR){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_ALREADY_EXIST){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
								    Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.EXCEED_SERVICE_LIMIT){
									Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_exceed_service_limit), Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();

		}
	}

	public void back(View view) {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
