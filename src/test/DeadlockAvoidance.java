package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	
	// 等待输入
	public void waitInput() {
		Scanner in = new Scanner(System.in);
		System.out.println("本系统共有"+n+"个进程"+m+"种资源，等待用户输入数据~");
		for(int j=0;j<m;j++) {
			System.out.println("请输入available["+j+"]");
			available[j] = in.nextInt();
		}
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				System.out.println("请输入max["+i+"]["+j+"]");
				max[i][j] = in.nextInt();
			}
		}
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				System.out.println("请输入allocation["+i+"]["+j+"]");
				allocation[i][j] = in.nextInt();
			}
		}
		
		// 下面为need数组进行赋值
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				need[i][j] = max[i][j] - allocation[i][j];
			}
		}
		print();
		
		inputRequest();
	}
	
	public void inputRequest() {
		System.out.println("请输入发起请求的进程号");
		Scanner in = new Scanner(System.in);
		int threadNumber = in.nextInt();
		System.out.println("您输入的是p"+threadNumber+"进程");
		System.out.println("该进程的需求量是:");
		for(int j=0;j<m;j++) {
			System.out.print(need[threadNumber][j]+"  ");
		}
		System.out.println();
		System.out.println("请输入请求资源的数目:");
		
		int[] newResource = new int[m];
		
		for(int j=0;j<m;j++) {
			newResource[j] = in.nextInt();
		}
		System.out.println("开始执行银行家算法，尝试进行分配");
		boolean result = tryAllocation(threadNumber, newResource);
		
		if(result) {
			System.out.println("分配成功");
		}else {
			System.out.println("分配失败");
		}
		
		System.out.println("更新数据，开始打印");
	
		print();
		inputRequest();
	}
	
	// 用于显示数据的函数
	public void print() {
		System.out.println("进程个数为:"+n);
		System.out.println("资源种数:"+m);
		System.out.println("|-------|-----------|-----------|-----------|-----------|");
		System.out.println("|       |----最大需求-|-已分配矩阵---|-需求矩阵----|--可用资源---|");
		System.out.println("|   资源   |     max   |Allocation |  Need     | Available |");
		System.out.println("|       |  A  B  C  |  A  B  C  |  A  B  C  |  A  B  C  |");
		System.out.println("|  进程      |           |           |           |           |");
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
		
		deadlockAvoidance.waitInput();
		
		
	}
}