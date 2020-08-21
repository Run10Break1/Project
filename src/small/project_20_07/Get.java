package small.project_20_07;

import java.util.*;
import java.io.*;

/*#### ���� ####
 ���� Ÿ���� ���� ���� ���ڰ� �����ڰ� �� �� ����
 ���ڿ� Ÿ���� ascii�� ����
 ������ ���� ������ ������ �����Ƿ� Ȯ�� �ʿ�
 (���� Ÿ���� long.MAX_VALUE �̳�, ���ڿ� Ÿ���� ��Ȳ�� ���� �ٸ�)
 
 �߰�
 EOF ���� ó�� �ʿ�
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
				// ������(long ���� �̳�) - BigInteger ���� x
				int[] tarr = new int[19];
				int t = 0, idx = 0, len = 0;
				while(48 <= (t = is.read()) && t <= 57) {
					tarr[idx++] = t - 48;
					len++;
				}
				if(t != dl[0] && t != 13 && t != 10 && t != -1) throw new Exception(); // ���ڸ� �� �а� �� �Ŀ� �̾����� �����ڰ� �־��� �����ڿ� ��ġ���� ����
				// EOF ���ɼ� �߰�
				if(t != dl[0]) {
					if(isLastValue) {
						if(osType == 0 && t != -1) {
							is.read();
						}
					} else throw new Exception(); // ������ ���� �ƴѵ����� �ұ��ϰ� �ٹٲ� ���ڰ� ���� (���� ���ʿ��� ó���ؾ� ��)
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
				// �Ǽ���
				// �Ҽ��� ������ ����Ȯ������ ���� �ϴ� ����
				throw new Exception();
			} else if(type == 3) {
				// String ��(�ִ� ���� 200����) ascii�� �ش��ϴ� ���ڸ�
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
					throw new Exception(); // 200���ڰ� �Ѵ� ���
				}
				if(check == 1) {
					if(!isLastValue) throw new Exception(); // ������ ���� �ƴѵ� �����ڰ� ĳ���� ���� �Ǵ� �� ������ ���
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
		//���� Ÿ��, delimiter, �� ���� ������ ������?,// �ٹٲ� ����
	}
	public Object[] get(int rowN, int colN, int type, String delimiter) {
		try {
			InputStream is = Get.is;
			byte[] dl = delimiter.getBytes();
			int dllen = dl.length;
			if(type == 1) {
				// ������(long ����)
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
					if(t != dl[0] && t != 10 && t != 13 && t != -1) throw new Exception(); // �������� ù���ڵ� �ƴϰ� �ٹٲ� ���ڵ� �ƴ� ���
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
				// �Ǽ���
				// �ε��Ҽ����� ����Ȯ������ ���ؼ� �ϴ� ����
				throw new Exception();
			} else if(type == 3) {
				// String��(�� ���� 20���� �̳�)
				String[][] Sarr = new String[rowN][colN];
				byte[] arr = new byte[20 + dllen];
				int check = 0, len = 0, arrl = arr.length;
				for(int ri = 0; ri < rowN; ri++) {
					// 0 ~ colN-2
					for(int ci = 0; ci < colN; ci++) {
c:						for(int i = 0; i < arrl; i++) {
							arr[i] = (byte)is.read();
							len++;
							// colN-1�� ����� ó��
							if(arr[i] == 10 || arr[i] == 13) {
								check = 3;
								break c;
							}
							if(i + 1 >= dllen) {
								for(int j = 0; j < dllen; j++) {
									if(dl[j] != arr[i - dllen + 1 + j]) continue c;
								}
								check = 1;
								// colN-1�� ����� ó��
								if(ci == colN - 1) check = 2;
								break c;
							}
			
						}
						if(check == 0) throw new Exception(); // ���ڰ� 20�� �̻��� ���
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
				// char��
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
					// EOF �߰���
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
