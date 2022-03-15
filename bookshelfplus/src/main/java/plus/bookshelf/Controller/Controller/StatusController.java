package plus.bookshelf.Controller.Controller;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import plus.bookshelf.Common.Response.CommonReturnType;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Api(value = "状态检测")
@Controller("status")
@RequestMapping("/status")
public class StatusController {

    // @ApiOperation(value = "线程CPU占用时间", notes = "获取服务器当前线程CPU占用时间。此方法通过统计线程CPU占用时间来统计当前进程占用CPU情况。")
    // @RequestMapping(value = "getProcessCpu", method = {RequestMethod.GET})
    // @ResponseBody
    // public Object get() {
    //     OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    //     ThreadMXBean threadBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    //     String processCpu = getProcessCpu(operatingSystemMXBean, threadBean);
    //
    //     System.out.println(processCpu);
    //     return processCpu;
    // }

    @ApiOperation(value = "系统状态", notes = "获取服务器当前系统负载。SystemLoadAverage返回-1时代表不支持。")
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType get() {
        // HashMap<String, Object> hashMap = new HashMap<>();
        // hashMap.put("server", "OK");
        // return CommonReturnType.create(hashMap);
        return CommonReturnType.create(null);
    }

    // @ApiOperation(value = "系统负载", notes = "获取服务器当前系统负载。SystemLoadAverage返回-1时代表不支持。")
    // @RequestMapping(value = "get", method = {RequestMethod.GET})
    // @ResponseBody
    // public Object get() {
    //     OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    //     return operatingSystemMXBean.getSystemLoadAverage();
    // }

    // @ApiOperation(value = "服务端状态", notes = "获取服务器当前状态信息")
    // @RequestMapping(value = "get", method = {RequestMethod.GET})
    // @ResponseBody
    // public Object get() {
    //     OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    //     ThreadMXBean threadBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    //     // com.sun.management.OperatingSystemMXBean mxbean = operatingSystemMXBean;
    //     // System.out.println("操作系统: " + mxbean.getName());
    //     // System.out.println("CPU数量:" + mxbean.getAvailableProcessors());
    //     // System.out.println("虚拟内存:" + mxbean.getCommittedVirtualMemorySize() / 1024 + "KB");
    //     // System.out.println("可用物理内存:" + mxbean.getFreePhysicalMemorySize() / 1024 + "KB");
    //     // System.out.println("Free Swap Space Size :" + mxbean.getFreeSwapSpaceSize() / 1024 + "KB");
    //     // System.out.println("Process CPU Time : " + mxbean.getProcessCpuTime());
    //     // System.out.println("总物理内存大小:" + mxbean.getTotalPhysicalMemorySize() / 1024 + "KB");
    //     // System.out.println("Total Swap Space Size:" + mxbean.getTotalSwapSpaceSize() / 1024 + "KB");
    //
    //     Map<String, Object> result = new HashMap<>();
    //     result.put("SystemCpuLoad", operatingSystemMXBean.getSystemCpuLoad());
    //     result.put("ProcessCpuLoad", operatingSystemMXBean.getProcessCpuLoad());
    //
    //     // result.put("ProcessCpuTime", operatingSystemMXBean.getProcessCpuTime());
    //
    //     // result.put("CommittedVirtualMemorySize", operatingSystemMXBean.getCommittedVirtualMemorySize());
    //
    //     // result.put("FreePhysicalMemorySize", operatingSystemMXBean.getFreePhysicalMemorySize());
    //     // result.put("TotalPhysicalMemorySize", operatingSystemMXBean.getTotalPhysicalMemorySize());
    //     result.put("PhysicalMemorySizeRate", 100l * operatingSystemMXBean.getFreePhysicalMemorySize() / operatingSystemMXBean.getTotalPhysicalMemorySize());
    //     // 可用物理内存百分比
    //
    //     // result.put("FreeSwapSpaceSize", operatingSystemMXBean.getFreeSwapSpaceSize());
    //     // result.put("TotalSwapSpaceSize", operatingSystemMXBean.getTotalSwapSpaceSize());
    //     result.put("SwapSpaceSizeRate", 100l * operatingSystemMXBean.getFreeSwapSpaceSize() / operatingSystemMXBean.getTotalSwapSpaceSize());
    //     // 可用交换空间百分比
    //
    //     // result.put("SystemLoadAverage", operatingSystemMXBean.getSystemLoadAverage());
    //
    //     result.put("aaa", getProcessCpu(operatingSystemMXBean, threadBean));
    //
    //     // Object[] a = new Object[]{
    //     //     // operatingSystemMXBean.getArch(),
    //     //     // operatingSystemMXBean.getName(),
    //     //     // operatingSystemMXBean.getSystemLoadAverage(),
    //     //     // operatingSystemMXBean.getVersion(),
    //     //     // operatingSystemMXBean.getAvailableProcessors(),
    //     //
    //     //     // operatingSystemMXBean.getSystemCpuLoad(),
    //     //     // operatingSystemMXBean.getProcessCpuLoad(),
    //     //     // operatingSystemMXBean.getProcessCpuTime(),
    //     //
    //     //     // operatingSystemMXBean.getCommittedVirtualMemorySize(),
    //     //
    //     //     // operatingSystemMXBean.getFreePhysicalMemorySize(),
    //     //     // operatingSystemMXBean.getTotalPhysicalMemorySize(),
    //     //
    //     //     // operatingSystemMXBean.getFreeSwapSpaceSize(),
    //     //     // operatingSystemMXBean.getTotalSwapSpaceSize(),
    //     // };
    //     return result;
    // }

    // // refer: https://blog.csdn.net/as403045314/article/details/101337176
    // private long preTime = System.nanoTime();
    // private long preUsedTime = 0;
    //
    // /**
    //  * getSystemLoadAverage()方法得到的操作系统统计的整个系统负载，不能较好的反应本进程的CPU占用情况
    //  * 此方法通过统计线程CPU占用时间来统计当前进程占用CPU情况
    //  *
    //  * @param osMxBean
    //  * @param threadBean
    //  * @return
    //  */
    // public String getProcessCpu(OperatingSystemMXBean osMxBean, ThreadMXBean threadBean) {
    //     long totalTime = 0;
    //     for (long id : threadBean.getAllThreadIds()) {
    //         totalTime += threadBean.getThreadCpuTime(id);
    //     }
    //     long curtime = System.nanoTime();
    //     long usedTime = totalTime - preUsedTime;
    //     long totalPassedTime = curtime - preTime;
    //     preTime = curtime;
    //     preUsedTime = totalTime;
    //     // return (((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors()) * 100;
    //     return String.valueOf(new BigDecimal(usedTime * 100 / (totalPassedTime * osMxBean.getAvailableProcessors())));
    // }
}
