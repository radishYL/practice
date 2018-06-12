package com.market.test;

import org.junit.Test;

import com.market.common.utils.IDGennerator;

public class Demo {
	
	@Test
	public void test(){
		
		IDGennerator idGen = IDGennerator.getInstance();
		
		int i = 0;
		while(i<=3000){
			System.out.println(idGen.genId());
			i++;
		}
	}
	
	@Test
	public void test1(){
		
		
		
	}
	
}
