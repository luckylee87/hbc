package com.wstro;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动SpringBoot会执行 @Order标识执行顺序 值越小越先执行
 * 
 */
@Component
@Order(1)
public class StartupRunner implements CommandLineRunner {
	private Logger logger = LoggerFactory.getLogger(StartupRunner.class);

	@Override
	public void run(String... args) throws Exception {
		logger.info(">>服务启动执行，执行加载数据等操作1<<");
	}

	public static String getCode(String base64) throws Exception {
		String imagecodeUrl;
//		if (AppSettings.isDebug())
			imagecodeUrl = "http://172.16.0.53:7777/captcha/v1/get_captcha";//测试地址
//		else
//			imagecodeUrl = "http://14.29.64.150:7777/captcha/v1/get_captcha";//默认公司外网访问地址
//		imagecodeUrl = "http://midea.powerbridge.com:7777/captcha/v1/get_captcha";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(imagecodeUrl);
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
		List<NameValuePair> data = new ArrayList<>();
		data.add(new BasicNameValuePair("imagecode", base64));
		data.add(new BasicNameValuePair("username", "ApiUser"));
		data.add(new BasicNameValuePair("passwd", "Powernet@2020"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String s = EntityUtils.toString(response.getEntity());
		ObjectMapper om = new ObjectMapper();
		JsonNode jn = om.readTree(s);
		String word = jn.get("captcha").asText();
		System.out.println(word);
		return word;
	}

	public static void main(String[] args) throws Exception {
		String s = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAWAEADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD2S9vrXTrGS6ufLSGBPMmkmk8pQp3AYdsIWLADBYYzkkcZxrPxJpmuTRaZpviPQ2vizJKba4EruAM5hQnqVO7PzhCCpD4JFnxmJP8AhXniAhlEX9kXm5SvzE+W2CDngdeMc5HIxzy2jS2OmaV8K7WOGSOS6RJQsKkpxZTbm2joS8+WbHPJY8UwOp1nxHbaF4i0ywvbVfsWoOtsl1HPueK4ct5aPFjIV9rAOCeVIIA5p9letq/im7ispIn0nTl8ieWKUEtd874WBUkhFKHKsuGO07vmC5/xBsrPVPAnidZIkkksoJpl89vNVZFt8ghQ3ykK3GcYb58Hgk+Hlrc2/wAOtDggjESG2t7jzfM5lMhEspw2/wDvkc4JO7AQbTQMrHxPq2t3lynhrTdOS0069S0vbjV7pot83SSCNUDYYFkXeSVLHChxzUb+P7L/AIQW98Qg2MNzbMVayuLkBRKhO+3WVSVeRhHIU28kNGSuDzDpHiOz8Gvrek6/qtnZNYX09zbRXErL9ptJ384SBnUtNIpMy4TOWXackhq5+w0q5svhl4w1bVBqNoNQbVr2GK7byGAlRY0FxH8oMjFSVABX5uOStAj1jTLvTdZ02DUNOnjurSdd0csbZDDofxBBBHUEEGm3ckUNzBbRqnmyq0hDMwIjXAJHBBO50GCRwxPbFVNO1nTdckvRoN9YXEYcLc3NnOrOrlDhsBSrEARgEkjqP4MGd7rdOqzJKCfMSOY/uo2O/wC5sZtzNtTIbbggEggNigZIVt5PLaa1ileP7juoJX5g3GRx8yqfqoPYVK80cjRs8CM0bbkLclTgjI44OCR9CaKKQijZLeRSvPeXMUtw8gJaC3EQMYUhYzksxAYs+d2cn0yDeiupBCgmCvKFG9kG1Se5AOcD2yfrRRQBn61p1lrmnXNncxyRG4VAbi3k8uZCjb42VxyCj/MO2e3JqPTNPfSo7VI767uBE07Sm5ZSZjK5kJIUKoYN0wMBSwxzkFFAGhNM7yJLF8ki/L8zMVKllLfKCAThcBj93PcEgrPcSShFTYq7sybgSWXB4HIwc4OeeARjnIKKAP/Z";
		getCode(s);
	}
}
