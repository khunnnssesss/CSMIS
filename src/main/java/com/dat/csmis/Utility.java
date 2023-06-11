package com.dat.csmis;

import javax.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteUrl(HttpServletRequest req) {
		String siteUrl=req.getRequestURL().toString();
		return siteUrl.replace(req.getServletPath(), "");
	}
}
