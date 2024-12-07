package com.batch.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@StepScope
@Slf4j

// 文字列を読み込むReader
// 型はString型で読み取る
public class Reader implements ItemReader<String>{
	// 繰り返し用のフィールド
	private int intIndex = 0;
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		// nullになるまで繰り返して表示する
		String[] readDataList = {"rice", "miso", "natto", null};
		
		// @Slf4jを適用しているためlog.infoとなる
		// System.out.printlnでもOKです
		// readDataListを1つずつ出力
		log.info("readData={}" , readDataList[intIndex]);
		
		// インクリメントする
		return readDataList[intIndex++];
	}

}
