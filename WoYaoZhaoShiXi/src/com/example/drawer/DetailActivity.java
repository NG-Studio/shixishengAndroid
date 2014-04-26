package com.example.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class DetailActivity extends Activity{
	
	private TextView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		content = (TextView) findViewById(R.id.detail_content);
		String html = "<div class='a-content-wrap'>发信人: baiduintern (搜研部实习生招聘), 信区: JobInfo <br> 标&nbsp;&nbsp;题: 【百度社招/实习生】【百度HR直聘】贴吧PHP研发工程师 <br> 发信站: 北邮人论坛 (Fri Apr 25 16:05:14 2014), 站内 <br>&nbsp;&nbsp;<br> 工作职责： <br> -百度贴吧产品研发工作&nbsp;&nbsp;<br> -技术调研、大数据量存储、性能优化等技术开发工作&nbsp;&nbsp;<br> -项目推动及项目管理相关工作 <br>&nbsp;&nbsp;<br> 职位要求： <br> -本科或本科以上学历，计算机相关专业者优先&nbsp;&nbsp; <br> -优秀的数据库设计和优化能力，至少精通一种数据库应用，精通Mysql数据库应用者优先&nbsp;&nbsp; <br> -能熟练应用一门或几门以下的技术：C/C++/Shell/JavaScript/Ajax&nbsp;&nbsp; <br> -扎实的计算机基础，较强的算法能力&nbsp;&nbsp;<br> -学习能力强，拥有优秀的逻辑思维能力&nbsp;&nbsp; <br> -有较好的沟通交流能力，能够迅速融入团队&nbsp;&nbsp;<br> -有良好的时间和流程意识&nbsp;&nbsp;<br> -有项目管理经验者优先 <br>&nbsp;&nbsp;<br> 实习生要求每周四天以上，实习时间至少三个月，六个月以上最佳。 <br>&nbsp;&nbsp;<br> 实习待遇：本科生 150+20饭补/天 <br> 研究生200+20饭补/天 <br> 正式员工待遇再议 <br>&nbsp;&nbsp;<br> 有意向者请发送简历至 baozhuo@baidu.com&nbsp;&nbsp;邮件格式： PHP实习/正式+姓名 请一定按格式发送邮件，标明实习或正式 <br></div>";
		
		
		content.setText(Html.fromHtml(html));
	
	}

}
