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
		String html = "<div class='a-content-wrap'>������: baiduintern (���в�ʵϰ����Ƹ), ����: JobInfo <br> ��&nbsp;&nbsp;��: ���ٶ�����/ʵϰ�������ٶ�HRֱƸ������PHP�з�����ʦ <br> ����վ: ��������̳ (Fri Apr 25 16:05:14 2014), վ�� <br>&nbsp;&nbsp;<br> ����ְ�� <br> -�ٶ����ɲ�Ʒ�з�����&nbsp;&nbsp;<br> -�������С����������洢�������Ż��ȼ�����������&nbsp;&nbsp;<br> -��Ŀ�ƶ�����Ŀ������ع��� <br>&nbsp;&nbsp;<br> ְλҪ�� <br> -���ƻ򱾿�����ѧ������������רҵ������&nbsp;&nbsp; <br> -��������ݿ���ƺ��Ż����������پ�ͨһ�����ݿ�Ӧ�ã���ͨMysql���ݿ�Ӧ��������&nbsp;&nbsp; <br> -������Ӧ��һ�Ż������µļ�����C/C++/Shell/JavaScript/Ajax&nbsp;&nbsp; <br> -��ʵ�ļ������������ǿ���㷨����&nbsp;&nbsp;<br> -ѧϰ����ǿ��ӵ��������߼�˼ά����&nbsp;&nbsp; <br> -�нϺõĹ�ͨ�����������ܹ�Ѹ�������Ŷ�&nbsp;&nbsp;<br> -�����õ�ʱ���������ʶ&nbsp;&nbsp;<br> -����Ŀ������������ <br>&nbsp;&nbsp;<br> ʵϰ��Ҫ��ÿ���������ϣ�ʵϰʱ�����������£�������������ѡ� <br>&nbsp;&nbsp;<br> ʵϰ������������ 150+20����/�� <br> �о���200+20����/�� <br> ��ʽԱ���������� <br>&nbsp;&nbsp;<br> ���������뷢�ͼ����� baozhuo@baidu.com&nbsp;&nbsp;�ʼ���ʽ�� PHPʵϰ/��ʽ+���� ��һ������ʽ�����ʼ�������ʵϰ����ʽ <br></div>";
		
		
		content.setText(Html.fromHtml(html));
	
	}

}
