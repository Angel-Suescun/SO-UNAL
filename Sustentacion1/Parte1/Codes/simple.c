
#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/hash.h>
#include <linux/gcd.h>
#include <linux/jiffies.h>
#include <asm/param.h>

static unsigned long start_jiffies; 


/* This function is called when the module is loaded. */
int simple_init(void)
{
    start_jiffies = jiffies;

    printk(KERN_INFO "Loading Kernel Module\n");
    printk(KERN_INFO "%u\n", GOLDEN_RATIO_PRIME);
    printk(KERN_INFO "The value of jiffies is: %lu\n", start_jiffies);
    printk(KERN_INFO "The value of HZ is: %d\n", HZ);
    
    return 0;
}
/* This function is called when the module is removed. */
void simple_exit(void)
{
    unsigned long elapsed_jiffies;
    unsigned long elapsed_seconds;
    unsigned long finish_jiffies = jiffies;

    elapsed_jiffies = finish_jiffies - start_jiffies;

    elapsed_seconds = elapsed_jiffies / HZ;
    
    printk(KERN_INFO "Removing Kernel Module\n");
    printk(KERN_INFO "The value of jiffies is: %lu\n", finish_jiffies);
    printk(KERN_INFO "Greatest Common Divisor: %u\n", gcd(3300, 24));
    printk(KERN_INFO "Elapsed time: %lu seconds\n", elapsed_seconds);

}


/* Macros for registering module entry and exit points. */
module_init(simple_init);
module_exit(simple_exit);

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("Simple Module");
MODULE_AUTHOR("SGG");