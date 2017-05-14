/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import java.util.Date;

@Table(name = "MT_MSG")
@BindID(name = "ID")
@SuppressWarnings("serial")
//参数表
public class Message extends BaseModel {
	private long ID; //主键
	private int PART;
	private String MSG_ID;
	private String ACCOUNT;
	private String MSG_SRC_CODE;
	private String MOBILE;
	private String CONTENT;
	private String REPORT;
	private Date REPORT_TIME;
	private String MONTH_SEQ;
	private String SP_CODE;
	private long PRODUCT_ID;
	private byte SUBMIT_TYPE;
	private String CLIENT_MSG_ID;


	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public int getPART() {
		return PART;
	}

	public void setPART(int PART) {
		this.PART = PART;
	}

	public String getMSG_ID() {
		return MSG_ID;
	}

	public void setMSG_ID(String MSG_ID) {
		this.MSG_ID = MSG_ID;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(String ACCOUNT) {
		this.ACCOUNT = ACCOUNT;
	}

	public String getMSG_SRC_CODE() {
		return MSG_SRC_CODE;
	}

	public void setMSG_SRC_CODE(String MSG_SRC_CODE) {
		this.MSG_SRC_CODE = MSG_SRC_CODE;
	}

	public String getMOBILE() {
		return MOBILE;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String CONTENT) {
		this.CONTENT = CONTENT;
	}

	public String getREPORT() {
		return REPORT;
	}

	public void setREPORT(String REPORT) {
		this.REPORT = REPORT;
	}

	public Date getREPORT_TIME() {
		return REPORT_TIME;
	}

	public void setREPORT_TIME(Date REPORT_TIME) {
		this.REPORT_TIME = REPORT_TIME;
	}

	public String getMONTH_SEQ() {
		return MONTH_SEQ;
	}

	public void setMONTH_SEQ(String MONTH_SEQ) {
		this.MONTH_SEQ = MONTH_SEQ;
	}

	public String getSP_CODE() {
		return SP_CODE;
	}

	public void setSP_CODE(String SP_CODE) {
		this.SP_CODE = SP_CODE;
	}

	public long getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(long PRODUCT_ID) {
		this.PRODUCT_ID = PRODUCT_ID;
	}

	public byte getSUBMIT_TYPE() {
		return SUBMIT_TYPE;
	}

	public void setSUBMIT_TYPE(byte SUBMIT_TYPE) {
		this.SUBMIT_TYPE = SUBMIT_TYPE;
	}

	public String getCLIENT_MSG_ID() {
		return CLIENT_MSG_ID;
	}

	public void setCLIENT_MSG_ID(String CLIENT_MSG_ID) {
		this.CLIENT_MSG_ID = CLIENT_MSG_ID;
	}
}
