package com.summerhotel.app;

import java.util.HashMap;
import java.util.Map;

import com.summerhotel.app.views.WelcomeView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import static com.summerhotel.app.Constant.*;
public class MainActivity extends Activity {      
	enum WhichView {MAIN_VIEW,//主界面
        LOGIN_VIEW,//登录界面
     REGISTER_VIEW,//注册斤面
     USERINFO_VIEW,//显示用户信息的界面，也可以
	  ALLRESOURCE_VIEW,//显示所有资源的界面
	RESOURCEDETAIL_VIEW,//显示所选资源详细信息的界面
	ORDERLIST_VIEW,//显示当前用户所有订单的界面
	ORDERDETAIL_VIEW,//订单详情的界面
	    KEFANG_VIEW,//显示客房资源的界面
	MEETINGROOM_VIEW,//显示会议室资源的界面
	   WELCOME_VIEW,
	   GY_VIEW,
	   HELP_VIEW
	          }

	String[] gNameAndgDetail;//分组资源名数组
	WelcomeView wv;
	WhichView curr;
	public static Handler hd;
	String sex="女";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        //设置全屏显示
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN , 
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //强制为竖屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
        gotoWelcomeView();
        
        hd=new Handler()
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        	   //调用父类处理  
        	   super.handleMessage(msg);
        	   //获取消息中的数据
        	   Bundle b;
			                    
			   b=msg.getData();
 			   //获取内容字符串
 			   String msgStr=b.getString("msg");
        	   //根据消息what编号的不同，执行不同的业务逻辑
        		switch(msg.what)
        		{
	        		case Constant.GOTOLOGIN:
	       			   gotoLoginView();
	       			   break;
	        		 case Constant.LOGINVIEW:

	        			  if(msgStr.equals("登录成功"))
	        			  {
	        				  Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
	   					      gotoMainView();
	        			  }
	        			  else
	        			  {
	        				  Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
	        			  }
					          
					 break;
	        		case Constant.REGISTER:
	        			   //获取消息中的数据
	        			   b=msg.getData();
	        			   //获取内容字符串
	        			   msgStr=b.getString("msg");
						   Toast.makeText(MainActivity.this, msgStr, Toast.LENGTH_SHORT).show();
						   System.out.println(msgStr);
						   if(msgStr.equals("注册成功,请输入用户名和密码以登录！"))//如果从jsp得到的字符串为登录成功，则转到首页
						   {
							   gotoLoginView();
						   }
	        		   break;
	        		case Constant.MAINVIEW:
	           			 //获取消息中的数据
	           			 b=msg.getData();
	           			 //获取内容字符串
	           			 msgStr=b.getString("msg");
	           			gNameAndgDetail=msgStr.split("\\|");
	           			
	           			TextView maintv1=(TextView)findViewById(R.id.mainTextView01);
	           			maintv1.setText(gNameAndgDetail[0]);
	           			TextView maintv2=(TextView)findViewById(R.id.mainTextView02);
	           			maintv2.setText(gNameAndgDetail[1]);
	           			TextView maintv3=(TextView)findViewById(R.id.mainTextView03);
	           			maintv3.setText(gNameAndgDetail[2]);
	           			TextView maintv4=(TextView)findViewById(R.id.mainTextView04);
	           			maintv4.setText(gNameAndgDetail[3]);
           			 break;
        		}
        	}
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	return true;
    }
    protected void gotoWelcomeView()
    {
    	if(wv==null)
    	{
    		wv=new WelcomeView(this);
    	}
    	setContentView(wv);
    	curr=WhichView.WELCOME_VIEW;//设置为欢迎界面
    }
    
    public void gotoMainView()
    {   	
    	setContentView(R.layout.main);
    	//准备URL
		final String url="http://"+IP_ADDRESS+":8080/Hotel/main.jsp"; 
		final Map<String,String> params=new HashMap<String,String>();
		params.put("params1","getGroupName");				
		//发送数据
		new Thread()
		{
			public void run()
			{
				String msgStr=HttpUploadUtil.postWithoutFile(url, params);//将url和参数传给jsp
				Bundle b=new Bundle();
    			//将内容字符串放进数据Bundle中
    			b.putString("msg", msgStr);
    			//创建消息对象
    			Message msg=new Message();
    			//设置数据Bundle到消息中
    			msg.setData(b);
    			//设置消息的what值
    			msg.what=Constant.MAINVIEW; 
    			//发送消息
    			hd.sendMessage(msg);
			}
		}.start();
		
    	TextView matv1=(TextView)findViewById(R.id.mainTextView01);
    	matv1.setOnClickListener(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) {
						//gotoKefang();
					}
    				
    			}
    	);
    	
    	TextView matv2=(TextView)findViewById(R.id.mainTextView02);
    	matv2.setOnClickListener(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) {
						//gotoKefang();
					}
    				
    			}
    	);
    	TextView matv3=(TextView)findViewById(R.id.mainTextView03);
    	matv3.setOnClickListener(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) {
						//gotoMeetingroom();
					}
    				
    			}
    	);
    	TextView matv4=(TextView)findViewById(R.id.mainTextView04);
    	matv4.setOnClickListener(
    			new OnClickListener()
    			{
					@Override
					public void onClick(View v) {
						//gotoMeetingroom();
					}
    				
    			}
    	);
    	
		ImageButton next=(ImageButton)findViewById(R.id.mainImageButton01);
        next.setOnClickListener(
        		new OnClickListener()
        		{

					@Override
					public void onClick(View v) {
						//gotoUserInfo();
					}
        			
        		}
        		);
    	
    	curr=WhichView.MAIN_VIEW;
    }
    
    public void gotoLoginView()
    {
    	setContentView(R.layout.login);
        
        Button b=(Button)this.findViewById(R.id.loginButton01);//登录键监听
        b.setOnClickListener
        (
        	new OnClickListener()
        	{
				@Override
				public void onClick(View v) 
				{					
					//准备URL
					final String url="http://"+IP_ADDRESS+":8080/Hotel/receive.jsp"; 
					
					//准备参数列表
					EditText et1=(EditText)findViewById(R.id.loginEditText01);
					EditText et2=(EditText)findViewById(R.id.loginEditText02);
					
					final Map<String,String> params=new HashMap<String,String>();
					params.put("params1",et1.getText().toString());				
					params.put("params2", et2.getText().toString());
					
					String username=et1.getText().toString();
					//获取SharedPreferences
				    SharedPreferences sp=MainActivity.this.getSharedPreferences("actm", Context.MODE_PRIVATE);
				    //从SharedPreferences中读取上次访问的时间
				    
			        String uname=sp.getString(username,null);
		        	SharedPreferences.Editor editor=sp.edit();
			        editor.putString("uname",username);
			        editor.commit();
					//发送数据
					new Thread()
					{
						public void run()
						{
							String msgStr=HttpUploadUtil.postWithoutFile(url, params);//将url和参数传给jsp
							Bundle b=new Bundle();
		        			//将内容字符串放进数据Bundle中
		        			b.putString("msg", msgStr);
		        			//创建消息对象
		        			Message msg=new Message();
		        			//设置数据Bundle到消息中
		        			msg.setData(b);
		        			//设置消息的what值
		        			msg.what=Constant.LOGINVIEW;
		        			//发送消息
		        			hd.sendMessage(msg);
						}
					}.start();	

				}        		
        	}
        );
        Button re=(Button)this.findViewById(R.id.loginButton02);//注册键监听
        re.setOnClickListener(
        	new OnClickListener()
        	{

				@Override
				public void onClick(View v) {
					gotoRegister();//去注册界面
				}
        		 
        	}
        );
    	curr=WhichView.LOGIN_VIEW;
    }
    protected void gotoRegister() {
		
        setContentView(R.layout.register); 
    	
    	Button re=(Button)this.findViewById(R.id.registerButton01);
    	re.setOnClickListener(
    			new OnClickListener()
    			{

					@Override
					public void onClick(View v) {
						
						
						//准备URL
						final String url="http://"+IP_ADDRESS+":8080/Hotel/register.jsp"; 
						
						//准备参数列表
						final EditText etyhm=(EditText)findViewById(R.id.registerEditText01);
						final EditText etmm=(EditText)findViewById(R.id.registerEditText02);
						final EditText etmmqr=(EditText)findViewById(R.id.registerEditText03);
						final EditText ettelnum=(EditText)findViewById(R.id.registerEditText04);
						final EditText etrealname=(EditText)findViewById(R.id.registerEditText05);
						    
						final EditText etemail=(EditText)findViewById(R.id.registerEditText07);
						if(etyhm.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this,"请输入用户名", Toast.LENGTH_SHORT).show();
						}
						else if(!(etyhm.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9_]{5,9}$")))
						{
							Toast.makeText(MainActivity.this,"用户名长度为6到10字节，允许数字下划线", Toast.LENGTH_SHORT).show();
						}
						else if(etmm.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this,"请输入密码", Toast.LENGTH_SHORT).show();
						}
						else if(etmm.getText().toString().length()<6)
						{
							Toast.makeText(MainActivity.this,"密码长度不能小于6", Toast.LENGTH_SHORT).show();
						}
						else if(etmmqr.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this,"请再次输入密码", Toast.LENGTH_SHORT).show();
						}
						else if(!etmm.getText().toString().trim().equals(etmmqr.getText().toString().trim()))
						{
							Toast.makeText(MainActivity.this,"密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
						}
						else if(ettelnum.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
						}
						else if(!ettelnum.getText().toString().matches("[0-9]{11}"))
						{
							Toast.makeText(MainActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
						}
						
						else if(etrealname.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
						}
//						else if(!etrealname.getText().toString().matches("[\u4e00-\u9fa5]"))
//						{
//							Toast.makeText(MainActivity.this, "姓名必须包含汉字", Toast.LENGTH_SHORT).show();
//						}
						else if(etemail.getText().toString().matches(""))
						{
							Toast.makeText(MainActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
						}
						else if(!etemail.getText().toString().matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"))
						{
							Toast.makeText(MainActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
						}
						else
						{ 
							
						final Map<String,String> params=new HashMap<String,String>();
						params.put("yonghuming", etyhm.getText().toString());				
						params.put("mm", etmm.getText().toString());
						params.put("telnum", ettelnum.getText().toString());
						params.put("realname", etrealname.getText().toString());
						params.put("email", etemail.getText().toString());
						RadioButton rbman=(RadioButton)findViewById(R.id.registerRadioButton01);
						if(rbman.isChecked())
						{
							sex="男";
						}
						System.out.print(sex);
						params.put("sex", sex);
						
						//发送数据
						new Thread()
						{
							public void run()
							{
								String msgStr=HttpUploadUtil.postWithoutFile(url, params);//将url和参数传给jsp
								Bundle b=new Bundle();
			        			//将内容字符串放进数据Bundle中
			        			b.putString("msg", msgStr);
			        			//创建消息对象
			        			Message msg=new Message();
			        			//设置数据Bundle到消息中
			        			msg.setData(b);
			        			//设置消息的what值
			        			msg.what=Constant.REGISTER;
			        			//发送消息
			        			hd.sendMessage(msg);
							}
						}.start();
						}
					}
    				
    			}
    			
    	);
    	re=(Button)findViewById(R.id.registerButton02);
    	re.setOnClickListener(
    			new OnClickListener()
    			{

					@Override
					public void onClick(View arg0) {
						gotoLoginView();
					}
    				
    			}
    			);
    	
    	curr=WhichView.REGISTER_VIEW;
	}
}
