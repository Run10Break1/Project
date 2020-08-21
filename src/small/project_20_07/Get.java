package small.project_20_07;

import java.util.*;
import java.io.*;

/*#### 주의 ####
 정수 타입을 받을 때는 숫자가 구분자가 될 수 없음
 문자열 타입은 ascii만 가능
 각각의 값은 범위가 정해져 있으므로 확인 필요
 (정수 타입은 long.MAX_VALUE 이내, 문자열 타입은 상황에 따라 다름)
 
 추가
 EOF 예외 처리 필요
*/
public class Get {
	static int osType = 0;
	static InputStream is = null;
	public Object get(int type, String delimiter, boolean isLastValue) {
		byte[] dl = delimiter.getBytes();
		int dllen = dl.length;
		InputStream is = Get.is;
		try {
			if(type == 1) {
				// 정수형(long 범위 이내) - BigInteger 지원 x
				int[] tarr = new int[19];
				int t = 0, idx = 0, len = 0;
				while(48 <= (t = is.read()) && t <= 57) {
					tarr[idx++] = t - 48;
					len++;
				}
				if(t != dl[0] && t != 13 && t != 10 && t != -1) throw new Exception(); // 숫자를 다 읽고 난 후에 이어지는 구분자가 주어진 구분자와 일치하지 않음
				// EOF 가능성 추가
				if(t != dl[0]) {
					if(isLastValue) {
						if(osType == 0 && t != -1) {
							is.read();
						}
					} else throw new Exception(); // 마지막 값이 아닌데에도 불구하고 줄바꿈 문자가 있음 (원래 위쪽에서 처리해야 함)
				} else {
					t = dllen;
					while(--t > 0) is.read();
					
					if(isLastValue) {
						if(osType == 0) {
							// window
							is.read();
							is.read();
						} else if(osType == 1) {
							// linux
							is.read();
						} else {
							System.out.println("caution");
						}
					}
				}
				
				long n = 0, w = 1;
				for(int i = len - 1; i >= 0; i--) {
					n += w * tarr[i];
					w *= 10;
				}
				return n;
			} else if(type == 2) {
				// 실수형
				// 소수점 연산의 부정확성으로 인해 일단 보류
				throw new Exception();
			} else if(type == 3) {
				// String 형(최대 길이 200글자) ascii에 해당하는 글자만
				byte[] arr = new byte[200];
				int check = 0, mark = 0;
c:				for(int i = 0; i < 200; i++) {
					mark = i;
					arr[i] = (byte)is.read();
					if(arr[i] == 10 || arr[i] == 13) {
						check = 1;
						mark--;
						break;
					}
					if(i + 1 >= dllen) {
						for(int j = dllen; j > 0; j--) {
							if(dl[dllen - j] != arr[i - dllen + 1]) continue c;
						}
						check = 2;
						mark -= dllen;
						break c;
					}
				}
				if(check == 0) {
					throw new Exception(); // 200글자가 넘는 경우
				}
				if(check == 1) {
					if(!isLastValue) throw new Exception(); // 마지막 값이 아닌데 구분자가 캐리지 리턴 또는 뉴 라인인 경우
					if(osType == 0) {
						is.read();
					}
				}
				if(check == 2) {
					if(isLastValue) {
						if(osType == 0) {
							is.read();
							is.read();
						} else if(osType == 1) {
							is.read();
						}	
					}
				}
				return new String(arr, 0, mark + 1);
			}
		} catch(Exception e) {
			System.out.println("Exception");
			return null;
		}
		System.out.println("type is not defined");
		return null;
		//값의 타입, delimiter, 한 줄의 마지막 값인지?,// 줄바꿈 종류
	}
	public Object[] get(int rowN, int colN, int type, String delimiter) {
		try {
			InputStream is = Get.is;
			byte[] dl = delimiter.getBytes();
			int dllen = dl.length;
			if(type == 1) {
				// 정수형(long 범위)
				int[] tarr = new int[19];
				long[][] Narr = new long[rowN][colN];
				long tn = 0;
				for(int ri = 0, cl = colN - 1; ri < rowN; ri++) {
					
					int t = 0, idx = 0, len = 0;
					// idx : 0 ~ colN-2
					for(int ci = 0; ci < cl; ci++) {
						while(48 <= (t = is.read()) && t <= 57) {
							tarr[idx++] = t - 48;
							len++;
						}
						if(t != dl[0]) throw new Exception();
						t = dllen;
						while(--t > 0) is.read();
						tn = 0;
						for(int ti = len - 1, w = 1; ti >= 0; ti--) {
							tn += w * tarr[ti];
							w *= 10;
						}
						Narr[ri][ci] = tn;
						
						idx = 0;
						len = 0;
					}
					
					// idx : colN-1
					while(48 <= (t = is.read()) && t <= 57) {
						tarr[idx++] = t - 48;
						len++;
					}
					if(t != dl[0] && t != 10 && t != 13 && t != -1) throw new Exception(); // 구분자의 첫문자도 아니고 줄바꿈 문자도 아닌 경우
					if(t == dl[0]) {
						t = dllen;
						while(--t > 0) is.read();
						is.read();
					}
					if(osType == 0 && t != -1) is.read();
					tn = 0;
					for(int ti = len - 1, w = 1; ti >= 0; ti--) {
						tn += w * tarr[ti];
						w *= 10;
					}
					Narr[ri][colN - 1] = tn;
				}
				return Narr;
			} else if(type == 2) {
				// 실수형
				// 부동소수점의 부정확성으로 인해서 일단 보류
				throw new Exception();
			} else if(type == 3) {
				// String형(한 값당 20문자 이내)
				String[][] Sarr = new String[rowN][colN];
				byte[] arr = new byte[20 + dllen];
				int check = 0, len = 0, arrl = arr.length;
				for(int ri = 0; ri < rowN; ri++) {
					// 0 ~ colN-2
					for(int ci = 0; ci < colN; ci++) {
c:						for(int i = 0; i < arrl; i++) {
							arr[i] = (byte)is.read();
							len++;
							// colN-1의 경우의 처리
							if(arr[i] == 10 || arr[i] == 13) {
								check = 3;
								break c;
							}
							if(i + 1 >= dllen) {
								for(int j = 0; j < dllen; j++) {
									if(dl[j] != arr[i - dllen + 1 + j]) continue c;
								}
								check = 1;
								// colN-1의 경우의 처리
								if(ci == colN - 1) check = 2;
								break c;
							}
			
						}
						if(check == 0) throw new Exception(); // 문자가 20자 이상일 경우
						else if(check == 1) {
							Sarr[ri][ci] = new String(arr, 0, len - dllen);
						}
						else if(check == 2) {
							if(osType == 0) {
								is.read();
								is.read();
							} else if(osType == 1) is.read();
							Sarr[ri][ci] = new String(arr, 0, len - dllen);
						}
						else if(check == 3) {
							if(osType == 0) is.read();
							Sarr[ri][ci] = new String(arr, 0, len - 1);
						}
						
						len = 0;
						check = 0;
					}
				}
				return Sarr;
			} else if(type == 4) {
				// char형
				char[][] Narr = new char[rowN][colN];
				int cl = colN - 1, t = 0;
				for(int ri = 0; ri < rowN; ri++) {
					
					for(int ci = 0; ci < cl; ci++) {
						Narr[ri][ci] = (char)is.read();
						is.skip(dllen);
					}
					Narr[ri][cl] = (char)is.read();
					t = is.read();
					if(t != 10 && t != 13 && t != dl[0] && t != -1) throw new Exception();
					// EOF 추가됨
					if(t == dl[0]) {
						is.skip(dllen);
					}
					if(osType == 0) {
						is.read();
					}
				}
				return Narr;
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
			return null;
		}
		System.out.println("type is not defined");
		return null;
	}
	public Get(String osType, InputStream is) {
		if(osType.equals("window")) Get.osType = 0;
		else if(osType.equals("linux")) Get.osType = 1;
		
		Get.is = is;
	}
	
}
