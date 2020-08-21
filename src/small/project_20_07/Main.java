package small.project_20_07;

import java.util.*;
import java.io.*;
import static small.project_20_07.FileGenerator.*;

public class Main {
	
	public void compare(int n) throws Exception {
		long[] om = new long[n];
		long[] nm = new long[n];
		long ostart = 0, oend = 0;
		long nstart = 0, nend = 0;
		/*
		String prePath = "C:\\Workspace_Java\\My Project\\src\\small\\project_20_07\\";
		File file = null;
		Get getter = null;
		Scanner sc = null;
		String[] info = new String[] {"Scanner","getter"};
		
		// 1. �ϳ��� ��(������x, ����)
		file = new File(prePath,"Integer[col=undefined, row=1].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			sc.nextLong();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			getter.get(1, " ", false);
		}
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		
		// 2. �ϳ��� ��(������x, ���ڿ�) 
		file = new File(prePath,"String[col=undefined, row=1].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			sc.next();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			getter.get(3, " ", false);
		}
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		
		// 3. �ϳ��� ��(������, ����)
		file = new File(prePath,"Integer[col=1, row=undefined].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			sc.nextLong();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			getter.get(1, " ", true);
		}
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		
		// 4. �ϳ��� ��(������, ���ڿ�)
		file = new File(prePath,"String[col=1, row=undefined].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			sc.next();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		for(int i = 0; i < 9999; i++) {
			getter.get(3, " ", true);
		}
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		
		// 5. �迭(���� Ÿ��)
		file = new File(prePath,"Integer[col=undefined, row=undefined].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		while(sc.hasNextLong()) {
			sc.nextLong();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		getter.get(10000, 10, 1, " ");
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		
		// 6. �迭(���ڿ� Ÿ��)
		file = new File(prePath,"String[col=undefined, row=undefined].txt");
		getter = new Get("window", new FileInputStream(file));
		
		sc = new Scanner(file);
		
		ostart = System.currentTimeMillis();
		while(sc.hasNext()) {
			sc.next();
		}
		oend = System.currentTimeMillis();
		
		
		nstart = System.currentTimeMillis();
		getter.get(10000, 10, 3, " ");
		nend = System.currentTimeMillis();
		
		TimeChecking.compareTime(oend - ostart, nend - nstart, info);
		*/
	}
	public static void main(String args[]) {
		Main main = new Main();
		long[] time = null;
		time = main.makeCompare("String", 20000, 20, 400000, 18, " ");
		TimeChecking.compareTime(time, new String[] {"scanner", "get"});
		System.out.println(time[0]+"ms "+time[1]+"ms");
		
	}
	static String directoryPath = "C:\\Workspace_Java\\My Project\\src\\small\\project_20_07\\";
	static String fileName = "{}[col={}, row={}].txt";
	public long[] makeCompare(String dataType, int row, int col, int n, int dataMaxLen, String delimiter) {
		// �迭�� n ���� 1 �ϼ��ۿ� ����.
		// scanner�� �� ����� �޼��� ������ ��ɿ� ���� �ٸ��Ƿ� �񱳸� �����ϰ� �� �� ����.
		File file = getFileTemplate(directoryPath+fileName, new Object[] {dataType, col, row});
		if(!file.exists()) makeFile(file, col, row, dataType, dataMaxLen, delimiter);
		Scanner sc = null;
		Get get = null;
		try {
			sc = new Scanner(new BufferedInputStream(new FileInputStream(file)));
			get = new Get("window", new BufferedInputStream(new FileInputStream(file)));
		} catch(Exception e) {
			
		}
				
		
		long[] time = new long[2];
		
		long start = 0, end = 0;
		start = System.currentTimeMillis();
		for(int i = 0; i < n; i++) {
			sc.next();
		}
		end = System.currentTimeMillis();
		time[0] = end - start;
		
		start = System.currentTimeMillis();
		for(int i = 0; i < 1; i++) {
			get.get(20000, 20, 3, " ");
		}
		end = System.currentTimeMillis();
		time[1] = end - start;
		
		return time;
	}
	private static void print(long v) {
		System.out.println(v);
	}
	private static void print(Object[] oa) {
		System.out.println(Arrays.deepToString(oa));
	}
	private static void print(Object o) { 
		System.out.println(o);
	}
	public static class TimeChecking {
		private static long getResult(long[] timeSet, int size, String info) {
			// �ݺ� Ƚ��
			// �ּڰ�, ��հ�, �ִ밪
			// ��հ� ��ȯ
			System.out.println("#"+info+" - "+size+"ȸ");
			Arrays.sort(timeSet);
			System.out.println("�ּڰ�(min) : "+getTimeFormat(timeSet[0]));
			long sum = 0, avg = 0;
			for(int i = 0; i < size; i++) {
				sum += timeSet[i];
			}
			avg = sum / size;
			System.out.println("��հ�(avg) : "+getTimeFormat(avg));
			System.out.println("�ִ�(max) : "+getTimeFormat(timeSet[size - 1]));
			return avg;
		}
		private static String getTimeFormat(long time) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			
			return cal.get(11)+"h "+
					cal.get(12)+"m "+
					cal.get(13)+"s "+
					cal.get(14)+"ms"; // constant field value ���� �Ұ�?
		}
		private static int compareTime(long[] time, String[] info) {
			long first = time[0], second = time[1];
			if(first < second) {
				System.out.println(info[0]+"(��)�� "+(second - first)+"ms ��ŭ �����ϴ�.");
				return 1;
			} else if(first > second) {
				System.out.println(info[1]+"(��)�� "+(first - second)+"ms ��ŭ �����ϴ�.");
				return 2;
			} else {
				System.out.println(info[0]+"�� "+info[1]+"�� �Ȱ��� �ð��� �ɸ��ϴ�.");
				return 0;
			}
		}
	}
	
}
