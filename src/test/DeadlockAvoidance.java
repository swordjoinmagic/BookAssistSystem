package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

import org.omg.PortableServer.POAPackage.WrongAdapter;

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
		
		//=======================================================
		// ����max
		deadlockAvoidance.max[0][0] = 7;
		deadlockAvoidance.max[0][1] = 5;
		deadlockAvoidance.max[0][2] = 3;
		
		deadlockAvoidance.max[1][0] = 3;
		deadlockAvoidance.max[1][1] = 2;
		deadlockAvoidance.max[1][2] = 2;
		
		deadlockAvoidance.max[2][0] = 9;
		deadlockAvoidance.max[2][1] = 0;
		deadlockAvoidance.max[2][2] = 2;
		
		deadlockAvoidance.max[3][0] = 2;
		deadlockAvoidance.max[3][1] = 2;
		deadlockAvoidance.max[3][2] = 2;
		
		deadlockAvoidance.max[4][0] = 4;
		deadlockAvoidance.max[4][1] = 3;
		deadlockAvoidance.max[4][2] = 3;
		//========================================================
		
		
		//==============================================
		// ����allocatioin
		deadlockAvoidance.allocation[0][0] = 0;
		deadlockAvoidance.allocation[0][1] = 1;
		deadlockAvoidance.allocation[0][2] = 0;
		
		deadlockAvoidance.allocation[1][0] = 2;
		deadlockAvoidance.allocation[1][1] = 0;
		deadlockAvoidance.allocation[1][2] = 0;
		
		deadlockAvoidance.allocation[2][0] = 3;
		deadlockAvoidance.allocation[2][1] = 0;
		deadlockAvoidance.allocation[2][2] = 2;
		
		deadlockAvoidance.allocation[3][0] = 2;
		deadlockAvoidance.allocation[3][1] = 1;
		deadlockAvoidance.allocation[3][2] = 1;
		
		deadlockAvoidance.allocation[4][0] = 0;
		deadlockAvoidance.allocation[4][1] = 0;
		deadlockAvoidance.allocation[4][2] = 2;
		
		//==================================================
		
		//=================================================
		// ����need����
		deadlockAvoidance.need[0][0] = 7;
		deadlockAvoidance.need[0][1] = 4;
		deadlockAvoidance.need[0][2] = 3;
		
		deadlockAvoidance.need[1][0] = 1;
		deadlockAvoidance.need[1][1] = 2;
		deadlockAvoidance.need[1][2] = 2;
		
		deadlockAvoidance.need[2][0] = 6;
		deadlockAvoidance.need[2][1] = 0;
		deadlockAvoidance.need[2][2] = 0;
		
		deadlockAvoidance.need[3][0] = 0;
		deadlockAvoidance.need[3][1] = 1;
		deadlockAvoidance.need[3][2] = 1;
		
		deadlockAvoidance.need[4][0] = 4;
		deadlockAvoidance.need[4][1] = 3;
		deadlockAvoidance.need[4][2] = 1;
		
		//==================================
		// ����available
		deadlockAvoidance.available[0] = 3;
		deadlockAvoidance.available[1] = 3;
		deadlockAvoidance.available[2] = 2;
		//==================================
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("��ʼ���Է�����Դ");
		int[] resources = new int[3];
		resources[0] = 1;
		resources[1] = 0;
		resources[2] = 2;
		
		
		// ���Ը�����1������Դ
		deadlockAvoidance.tryAllocation(1, resources);
		
		int[] resouces2 = new int[3];
		resouces2[0] = 0;
		resouces2[1] = 2;
		resouces2[2] = 0;
		deadlockAvoidance.tryAllocation(0, resouces2);
		
		
	}
}