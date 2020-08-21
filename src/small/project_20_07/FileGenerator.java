package small.project_20_07;

import java.io.*;
import java.util.*;

public class FileGenerator {
	public static File getFileTemplate(String template, Object[] s) {
		StringBuilder sb = new StringBuilder();
		String[] splitStr = template.split("\\{\\}");
		int len = 0;
		if(splitStr.length - 1 != (len = s.length)) {
			System.out.println("template error");
			return null;
		}
		for(int i = 0; i < len; i++) {
			sb.append(splitStr[i]).append(s[i]);
		}
		sb.append(splitStr[len]);
		return new File(sb.toString());
	}
	
	public static boolean makeFile(File file, int col, int row, String dataType, int dataMaxLen, String delimiter) {
		// 파일이 존재하지 않을 경우
		try {
			file.createNewFile();
		} catch(Exception e) {
			System.out.println("during file creation, error is occured");
			return false;
		}
		// 파일 내 데이터 셋 생성
		int len = delimiter.length();
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			String[][] data = makeData(col, row, dataType, dataMaxLen);
			
			for(int ri = 0; ri < row; ri++) {
				for(int ci = 0; ci < col; ci++) {
					bw.write(data[ri][ci], 0, data[ri][ci].length());
					bw.write(delimiter, 0, len);
				}
				bw.newLine();
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	static long[] w = new long[] {
		1,
		10,
		100,
		1000,
		10000,
		100000,
		1000000,
		10000000,
		100000000,
		1000000000,
		10000000000l,
		100000000000l,
		1000000000000l,
		10000000000000l,
		100000000000000l,
		1000000000000000l,
		10000000000000000l,
		100000000000000000l,
	};
	/*
	static char[] c = new char[] { 
			'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	*/
	private static String[][] makeData(int col, int row, String dataType, int dataMaxLen) {
		String[][] Sarr = new String[row][col];
		if(dataType.equals("Integer")) {
			// 정수형(18자리까지 가능)
			long t = 0;
			for(int ri = 0; ri < row; ri++) {
				for(int ci = 0; ci < col; ci++) {
					t = 0;
					for(int i = 0; i < dataMaxLen; i++) {
						int v = (int)(Math.random() * 10);
						if(v == 0) break;
						t += w[i] * v;
					}
					Sarr[ri][ci] = String.valueOf(t);
				}
			}
			return Sarr;
		} else if(dataType.equals("String")) {
			// 문자형(영대소문자)
			StringBuilder t = new StringBuilder();
			for(int ri = 0; ri < row; ri++) {
				for(int ci = 0; ci < col; ci++) {
					for(int i = 0; i < dataMaxLen; i++) {
						char c = (char)(33 + (int)(Math.random() * 94));
						t.append(c);
						if(90 <= c && c <= 104) break;
					}
					Sarr[ri][ci] = t.toString();
					t.delete(0,t.length());
				}
			}
			return Sarr;
		} else {
			System.out.println("type is not defined");
			return null;
		}
	}
	public static boolean deleteFile(File file) {
		if(!file.isFile()) {
			System.out.println(file+" is not file");
			return false;
		}
		try {
			file.delete();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
