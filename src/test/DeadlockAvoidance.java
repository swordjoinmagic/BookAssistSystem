package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

import org.omg.PortableServer.POAPackage.WrongAdapter;

/*
 * 用于避免死锁的银行家算法
 */
public class DeadlockAvoidance {
	
	// 系统中的进程总数
	public int n;
	
	// 系统中的资源种数
	public int m;
	
	// Available数组，为可利用资源数组，记录这三类资源
	// 每一个当前可以使用的数量，初始值是系统中所配置的该类全部可用资源的数目
	public int[] available;	
	
	// 最大需求矩阵Max，是一个n*m的矩阵（n是系统中的进程数量，m是系统中可分配资源的种数（记住不是总数））
	// 它表示n个进程中每一个进程对m类资源的最大需求，举例：
	// 如果Max[i][j] = K，说明，进程i需要j类资源最大数目为K
	public int[][] max;
	
	// 分配矩阵，是一个n*m矩阵，n，m含义同上，它表示当前系统每个进程已经被分配到的资源数目，举个例子：
	// 如果allocation[i][j] = k,表示第i个进程已经拿到了k个j类资源
	public int[][] allocation;
	
	// 需求矩阵，n*m数组，n、含义同上，用来表示每个进程还需要的资源数，举个例子：
	// 如果need[i][j] = K，说明第i个进程还需要K个j类资源
	public int[][] need;
	
	// 根据进程数和资源数初始化银行家算法里面需要的各个数组
	/**
	 * 
	 * @param n 进程数
	 * @param m 资源种数
	 */
	public DeadlockAvoidance(int n,int m) {
		this.n = n;
		this.m = m;
		
		available = new int[m];
		max = new int[n][m];
		allocation = new int[n][m];
		need = new int[n][m];
	}
	
	// 对系统目前的状态进行安全性检测，返回true表示安全，false相反
	public boolean safetyTest() {
		
		System.out.println("开始安全性检测");
		
		// 工作向量，长度为m，用来表示系统可以提供给进程继续运行所需的各类资源数目，
		// 相当于available数组，在执行安全性检测算法开始时，Work=Available,
		// 通俗易懂的说，Work就是目前还可以进行继续分配的各类资源总数
		int[] work = new int[m];
		System.out.println(available.length);
		// 初始化work
		for(int i=0;i<available.length;i++) {
			work[i] = available[i];
		}
		
		// 用于表示某个进程是否运行完成，运行完成的进程不再进入安全性检测算法中
		boolean[] finish = new boolean[n];
		
		// 下面开始安全性检测
		
		// 第一步，找到一个满足一下条件的进程:
		// Finish[i] = false;
		// Need[i][j] < work[j]
		// 这里的小于要说明一下，比如说一共有三类资源A/B/C，那么这个小于就是指
		// Need[i][A] < work[A] and Need[i][B] < work[B] and Need[i][C] < work[C]
		// 只有满足这样的条件，才说明这个进程是需要的资源小于可以支配的资源数量，也就是，它是可以分配的
		
		List<Integer>list = new ArrayList<>();
		
		// 无限循环，直到不满足条件，或者finish全为true为止
		while(true) {
			int processNumber = -1;
			
			for(int i=0;i<n;i++) {
				if(!finish[i]&&islt(need, work, i)) {
					// 已找到满足条件的进程
					processNumber = i;
					break;
				}
			}
			
			// 如果processNumber等于-1，说明没有找到，这个时候要看finish是否全部都是true了，如果是的话
			// 那么表示这个系统目前是安全的，如果不是，说明系统进入非安全状态
			if(processNumber != -1) {
				
				list.add(processNumber);
				
				// 找到该进程后，进程i获得资源，顺利进行，直到完成，并释放出分配给它的资源
				finish[processNumber] = true;
				
				// 进程i释放掉自己有的全部资源
				for(int j=0;j<m;j++) {
					work[j] += allocation[processNumber][j];
				}
				
			}else {
				// 如果不是全是true，则系统不安全，返回false
				boolean result = isAllTrue(finish);
				
				
				
				if(result) {
					// 输出安全序列
					System.out.print("找到一个安全序列:   ");
					for(int i=0;i<list.size();i++) {
						if(i==0)
							System.out.print("P"+list.get(i));
						else
							System.out.print("-->P"+list.get(i));
					}
					System.out.println();		
				}else {
					System.out.println("系统没有通过安全性检测!");
				}
				
				
				
				return result;
			}
		}
	}
	
	// 用于检查finish数组是不是全是true，如果全是true，返回true，否则返回false
	public boolean isAllTrue(boolean[] finish) {
		for(int i=0;i<finish.length;i++) {
			if(!finish[i]) {
				return false;
			}
		}
		return true;
	}
	
	// 用于检查是Need[i][j]是不是小于work[j]，小于返回true，否则返回false
	public boolean islt(int[][] need,int[] work,int i) {
		for(int j=0;j<m;j++) {
			System.out.println(j);
			if(need[i][j]>work[j]) {
				return false;
			}
		}
		return true;
	}

	// 尝试给进程i分配资源,resources是资源数组，长度为m
	// 分配资源是这样的：
	// 给进程i的0类资源分配resources[0]个
	// 给进程i的1类资源分配resources[1]个
	// ................................
	// 给进程i的m类资源分配resources[m]个
	public boolean tryAllocation(int i,int[] resources) {
		
		// 先尝试进行资源分配
		for(int j=0;j<resources.length;j++) {
			allocation[i][j] += resources[j];
			available[j] -= resources[j];
			need[i][j] -= resources[j];
		}
		
		// 进行安全性检验
		boolean result = safetyTest();
		
		if(result) {
			// 通过安全性检测，说明这个分配是ok
			return true;
		}else {
			// 没有通过安全性检测，将前面 分配的资源还原回来
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
		
		// 五个进程，三类资源
		DeadlockAvoidance deadlockAvoidance = new DeadlockAvoidance(5,3);
		
		//=======================================================
		// 设置max
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
		// 设置allocatioin
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
		// 设置need数组
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
		// 设置available
		deadlockAvoidance.available[0] = 3;
		deadlockAvoidance.available[1] = 3;
		deadlockAvoidance.available[2] = 2;
		//==================================
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("开始尝试分配资源");
		int[] resources = new int[3];
		resources[0] = 1;
		resources[1] = 0;
		resources[2] = 2;
		
		
		// 尝试给进程1分配资源
		deadlockAvoidance.tryAllocation(1, resources);
		
		int[] resouces2 = new int[3];
		resouces2[0] = 0;
		resouces2[1] = 2;
		resouces2[2] = 0;
		deadlockAvoidance.tryAllocation(0, resouces2);
		
		
	}
}