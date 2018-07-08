package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 * ���ڱ������������м��㷨
 */
public class DeadlockAvoidance {
	
	// ϵͳ�еĽ�������
	public int n;
	
	// ϵͳ�е���Դ����
	public int m;
	
	// Available���飬Ϊ��������Դ���飬��¼��������Դ
	// ÿһ����ǰ����ʹ�õ���������ʼֵ��ϵͳ�������õĸ���ȫ��������Դ����Ŀ
	public int[] available;	
	
	// ����������Max����һ��n*m�ľ���n��ϵͳ�еĽ���������m��ϵͳ�пɷ�����Դ����������ס������������
	// ����ʾn��������ÿһ�����̶�m����Դ��������󣬾�����
	// ���Max[i][j] = K��˵��������i��Ҫj����Դ�����ĿΪK
	public int[][] max;
	
	// ���������һ��n*m����n��m����ͬ�ϣ�����ʾ��ǰϵͳÿ�������Ѿ������䵽����Դ��Ŀ���ٸ����ӣ�
	// ���allocation[i][j] = k,��ʾ��i�������Ѿ��õ���k��j����Դ
	public int[][] allocation;
	
	// �������n*m���飬n������ͬ�ϣ�������ʾÿ�����̻���Ҫ����Դ�����ٸ����ӣ�
	// ���need[i][j] = K��˵����i�����̻���ҪK��j����Դ
	public int[][] need;
	
	// ���ݽ���������Դ����ʼ�����м��㷨������Ҫ�ĸ�������
	/**
	 * 
	 * @param n ������
	 * @param m ��Դ����
	 */
	public DeadlockAvoidance(int n,int m) {
		this.n = n;
		this.m = m;
		
		available = new int[m];
		max = new int[n][m];
		allocation = new int[n][m];
		need = new int[n][m];
	}
	
	// �ȴ�����
	public void waitInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("��ϵͳ����"+n+"������"+m+"����Դ���ȴ��û���������~");
		for(int j=0;j<m;j++) {
			System.out.println("������available["+j+"]");
			available[j] = in.nextInt();
		}
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				System.out.println("������max["+i+"]["+j+"]");
				max[i][j] = in.nextInt();
			}
		}
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				System.out.println("������allocation["+i+"]["+j+"]");
				allocation[i][j] = in.nextInt();
			}
		}
		
		// ����Ϊneed������и�ֵ
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				need[i][j] = max[i][j] - allocation[i][j];
			}
		}
		print();
		
		inputRequest();
	}
	
	public void inputRequest() {
		System.out.println("�����뷢������Ľ��̺�");
		Scanner in = new Scanner(System.in);
		int threadNumber = in.nextInt();
		System.out.println("���������p"+threadNumber+"����");
		System.out.println("�ý��̵���������:");
		for(int j=0;j<m;j++) {
			System.out.print(need[threadNumber][j]+"  ");
		}
		System.out.println();
		System.out.println("������������Դ����Ŀ:");
		
		int[] newResource = new int[m];
		
		for(int j=0;j<m;j++) {
			newResource[j] = in.nextInt();
		}
		System.out.println("��ʼִ�����м��㷨�����Խ��з���");
		boolean result = tryAllocation(threadNumber, newResource);
		
		if(result) {
			System.out.println("����ɹ�");
		}else {
			System.out.println("����ʧ��");
		}
		
		System.out.println("�������ݣ���ʼ��ӡ");
	
		print();
		inputRequest();
	}
	
	// ������ʾ���ݵĺ���
	public void print() {
		System.out.println("���̸���Ϊ:"+n);
		System.out.println("��Դ����:"+m);
		System.out.println("|-------|-----------|-----------|-----------|-----------|");
		System.out.println("|       |----�������-|-�ѷ������---|-�������----|--������Դ---|");
		System.out.println("|   ��Դ   |     max   |Allocation |  Need     | Available |");
		System.out.println("|       |  A  B  C  |  A  B  C  |  A  B  C  |  A  B  C  |");
		System.out.println("|  ����      |           |           |           |           |");
		System.out.println("|-------|-----------|-----------|-----------|-----------|");
		
		for(int i=0;i<n;i++) {
			System.out.print("|  p"+i+"   |");
			
			for(int j=0;j<m;j++) {
				System.out.print("  "+max[i][j]);
			}
			System.out.print("  ");
			for(int j=0;j<m;j++) {
				System.out.print("  "+allocation[i][j]);
			}
			System.out.print("  ");
			for(int j=0;j<m;j++) {
				System.out.print("  "+need[i][j]);
			}
			System.out.print("  ");
			if(i==0)
				for(int j=0;j<m;j++) {
					System.out.print("  "+available[j]);
				}
			else 
				System.out.print("            |");
			System.out.println();
		}
		System.out.println("|-------|-----------|-----------|-----------|-----------|");

	}
	
	// ��ϵͳĿǰ��״̬���а�ȫ�Լ�⣬����true��ʾ��ȫ��false�෴
	public boolean safetyTest() {
		
		System.out.println("��ʼ��ȫ�Լ��");
		
		// ��������������Ϊm��������ʾϵͳ�����ṩ�����̼�����������ĸ�����Դ��Ŀ��
		// �൱��available���飬��ִ�а�ȫ�Լ���㷨��ʼʱ��Work=Available,
		// ͨ���׶���˵��Work����Ŀǰ�����Խ��м�������ĸ�����Դ����
		int[] work = new int[m];
		System.out.println(available.length);
		// ��ʼ��work
		for(int i=0;i<available.length;i++) {
			work[i] = available[i];
		}
		
		// ���ڱ�ʾĳ�������Ƿ�������ɣ�������ɵĽ��̲��ٽ��밲ȫ�Լ���㷨��
		boolean[] finish = new boolean[n];
		
		// ���濪ʼ��ȫ�Լ��
		
		// ��һ�����ҵ�һ������һ�������Ľ���:
		// Finish[i] = false;
		// Need[i][j] < work[j]
		// �����С��Ҫ˵��һ�£�����˵һ����������ԴA/B/C����ô���С�ھ���ָ
		// Need[i][A] < work[A] and Need[i][B] < work[B] and Need[i][C] < work[C]
		// ֻ��������������������˵�������������Ҫ����ԴС�ڿ���֧�����Դ������Ҳ���ǣ����ǿ��Է����
		
		List<Integer>list = new ArrayList<>();
		
		// ����ѭ����ֱ������������������finishȫΪtrueΪֹ
		while(true) {
			int processNumber = -1;
			
			for(int i=0;i<n;i++) {
				if(!finish[i]&&islt(need, work, i)) {
					// ���ҵ����������Ľ���
					processNumber = i;
					break;
				}
			}
			
			// ���processNumber����-1��˵��û���ҵ������ʱ��Ҫ��finish�Ƿ�ȫ������true�ˣ�����ǵĻ�
			// ��ô��ʾ���ϵͳĿǰ�ǰ�ȫ�ģ�������ǣ�˵��ϵͳ����ǰ�ȫ״̬
			if(processNumber != -1) {
				
				list.add(processNumber);
				
				// �ҵ��ý��̺󣬽���i�����Դ��˳�����У�ֱ����ɣ����ͷų������������Դ
				finish[processNumber] = true;
				
				// ����i�ͷŵ��Լ��е�ȫ����Դ
				for(int j=0;j<m;j++) {
					work[j] += allocation[processNumber][j];
				}
				
			}else {
				// �������ȫ��true����ϵͳ����ȫ������false
				boolean result = isAllTrue(finish);
				
				
				
				if(result) {
					// �����ȫ����
					System.out.print("�ҵ�һ����ȫ����:   ");
					for(int i=0;i<list.size();i++) {
						if(i==0)
							System.out.print("P"+list.get(i));
						else
							System.out.print("-->P"+list.get(i));
					}
					System.out.println();		
				}else {
					System.out.println("ϵͳû��ͨ����ȫ�Լ��!");
				}
				
				
				
				return result;
			}
		}
	}
	
	// ���ڼ��finish�����ǲ���ȫ��true�����ȫ��true������true�����򷵻�false
	public boolean isAllTrue(boolean[] finish) {
		for(int i=0;i<finish.length;i++) {
			if(!finish[i]) {
				return false;
			}
		}
		return true;
	}
	
	// ���ڼ����Need[i][j]�ǲ���С��work[j]��С�ڷ���true�����򷵻�false
	public boolean islt(int[][] need,int[] work,int i) {
		for(int j=0;j<m;j++) {
			System.out.println(j);
			if(need[i][j]>work[j]) {
				return false;
			}
		}
		return true;
	}

	// ���Ը�����i������Դ,resources����Դ���飬����Ϊm
	// ������Դ�������ģ�
	// ������i��0����Դ����resources[0]��
	// ������i��1����Դ����resources[1]��
	// ................................
	// ������i��m����Դ����resources[m]��
	public boolean tryAllocation(int i,int[] resources) {
		
		// �ȳ��Խ�����Դ����
		for(int j=0;j<resources.length;j++) {
			allocation[i][j] += resources[j];
			available[j] -= resources[j];
			need[i][j] -= resources[j];
		}
		
		// ���а�ȫ�Լ���
		boolean result = safetyTest();
		
		if(result) {
			// ͨ����ȫ�Լ�⣬˵�����������ok
			return true;
		}else {
			// û��ͨ����ȫ�Լ�⣬��ǰ�� �������Դ��ԭ����
			for(int j=0;j<resources.length;j++) {
				allocation[i][j] -= resources[j];
				available[j] += resources[j];	
				need[i][j] += resources[j];
			}
			return false;
		}
	}
}
class Testttt{
	
	  
	public static void main(String[] args) {
		
		// ������̣�������Դ
		DeadlockAvoidance deadlockAvoidance = new DeadlockAvoidance(5,3);
		
		deadlockAvoidance.waitInput();
		
		
	}
}