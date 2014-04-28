package com.t3.model.chat;

import com.t3.GUID;
import com.t3.client.TabletopTool;
import com.t3.model.Token;

public class TokenSpeaker extends Speaker {
	
	private Token token;
	
	public TokenSpeaker(String identity) {
		super(identity);
	}

	@Override
	public String toHTML() {
		Token t=getToken();
		return t.getName()+"<br><img src=\"asset://"+t.getImageAssetId()+"-30\"/>";
	}

	@Override
	public String toString() {
		return getToken().getName();
	}
	
	private Token getToken() {
		if(token==null)
			token=TabletopTool.getFrame().findToken(new GUID(getIdentity()));
		return token;
	}
}