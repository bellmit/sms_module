/**********************************************************************************************************************
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.                                       *
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.                        *
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.                                                   *
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.                     *
 * Vestibulum commodo. Ut rhoncus gravida arcu.                                                                       *
 **********************************************************************************************************************/

package z.z.w.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*********************************************************************************************
 * <pre>
 *     FileName: z.z.w.returnBrokenResource.CountDownTest
 *         Desc:
 *       author: Z_Z.W - myhongkongzhen@gmail.com
 *      version: 2015-10-15 12:26
 *   LastChange: 2015-10-15 12:26
 *      History:
 * </pre>
 *********************************************************************************************/
public class CountDownTest
{
	public static void main( String[] args )
	{
		try
		{
			// 开始的倒数锁
			final CountDownLatch begin = new CountDownLatch( 1 );
			// 结束的倒数锁
			final CountDownLatch end = new CountDownLatch( 10 );
			// 十名选手
			final ExecutorService exec = Executors.newFixedThreadPool( 4 );

			for ( int index = 0 ; index < 10 ; index++ )
			{
				final int NO = index + 1;
				Runnable run = new Runnable()
				{
					public void run()
					{
						try
						{
							System.out.println( "===No." + NO + " arrived==>" + Thread.currentThread().getName() );
							begin.await();//一直阻塞
							Thread.sleep( ( long ) ( Math.random() * 10000 ) );
//							System.out.println( "No." + NO + " arrived==>" + Thread.currentThread().getName() );
						}
						catch ( InterruptedException e )
						{
						}
						finally
						{
							end.countDown();
						}
					}
				};
				exec.submit( run );
			}
			System.out.println( "Game Start" );
			begin.countDown();
			end.await();
			System.out.println( "Game Over" );
			exec.shutdown();
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
