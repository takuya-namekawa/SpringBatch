package com.batch.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j
// 型は文字列を受け取り文字列を返す
public class Processor implements ItemProcessor<String, String>{

	@Override
	public String process(String item) throws Exception {
		
		// 読み込んだ文字列を大文字に変換して返す
		item = item.toUpperCase();
		
		log.info("processor item={}" , item);
		return item;
	}

}
