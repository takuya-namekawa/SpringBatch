package com.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class Tasklet1JobParametersValidator implements JobParametersValidator{

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		
		// StringParamの値を確認する
		// パラメーターの値を取得する 引数には取得したい値のキーを設定する
		String StringParam = parameters.getString("StringParam");
		
		if (!StringParam.equals("Java") && !StringParam.equals("Ruby")) {
			throw new JobParametersInvalidException("StringParamの値は「Java」「Ruby」を受け付けます。　入力された値 : 」" + StringParam);
		}
		
		// IntegerParamの型を確認する
		String IntegerParam = parameters.getString("IntegerParam");
		
		// 数値チェック
		try {
			Integer.parseInt(IntegerParam);
		} catch (Exception e) {
			throw new JobParametersInvalidException("IntegerParamは数値のみ受け付けます　入力された値 : " + IntegerParam);
		}
	}

}
