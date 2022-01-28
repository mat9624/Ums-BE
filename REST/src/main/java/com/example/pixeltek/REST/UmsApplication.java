package com.example.pixeltek.REST;


import com.example.pixeltek.REST.configuration.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({
        WebConfiguration.class})
@SpringBootApplication
@ComponentScan("com.example.pixeltek")
public class UmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }

    public void runGC(){ // metodo per test sull'utilizzo della memoria
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memoryUsedPercent: " + memoryUsedPercent);
        if (memoryUsedPercent > 90.0)
            System.gc();
    }

}
