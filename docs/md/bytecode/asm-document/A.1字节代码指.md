# A.1 字节代码指令

本节对字节代码指令进行简要描述。如需全面描述，请参阅 Java 虚拟机规范。

约定：a 和 b 表示 int, float, long 或 double 值（比如，它们对于 IADD 表示 int，而对于 LADD 则表示 long），o 和 p 表示对象引用，v 表示任意值（或者，对于栈指令，表示大小为 1 的值），w 表示 long 或 double，i、j 和 n 表示 int 值。

>局部变量

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
| ILOAD, LLOAD, FLOAD, DLOAD var | ... | ... , a |
| ALOAD var | ... | ... , o |
| ISTORE, LSTORE, FSTORE, DSTORE var | ... , a |  ...|
| ASTORE var | ... , o| ... |
| IINC var incr | ... | ... |

>栈     

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	POP	|	... , v	|	...	|
|	POP2	|	... , v1 , v2	|	...	|
|	POP2	|	... , w	|	...	|
|	DUP	|	... , v	|	... , v , v	|
|	DUP2	|	... , v1 , v2	|	... , v1 , v2 , v1 , v2	|
|	DUP2	|	... , w	|	... , w, w	|
|	SWAP	|	... , v1 , v2	|	... , v2 , v1	|
|	DUP_X1	|	... , v1 , v2	|	... , v2 , v1 , v2	|
|	DUP_X2	|	... , v1 , v2 , v3	|	... , v3 , v1 , v2 , v3	|
|	DUP_X2	|	... , w , v	|	... , v , w , v	|
|	DUP2_X1	|	... , v1 , v2 , v3	|	... , v2 , v3 , v1 , v2 , v3	|
|	DUP2_X1	|	... , v , w	|	... , w , v , w	|
|	DUP2_X2	|	... , v1 , v2 , v3 , v4	|	... , v3 , v4 , v1 , v2 , v3 , v4	|
|	DUP2_X2	|	... , w , v1 , v2	|	... , v1 , v2 , w , v1 , v2	|
| DUP2_X2 |.... , v1 , v2 , w |	...	,	w	,		v1	,		v2	,	w|
| DUP2_X2 |... , w1 , w2 |	...	,	w2		,	w1		,	w2	|
	
>常量

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
| ICONST_n (−1 _ n _ 5) | ...	| ... , n |
| LCONST_n (0 _ n _ 1) | ...	| ... , nL |
| FCONST_n (0 _ n _ 2) | ...	| ... , nF |
| DCONST_n (0 _ n _ 1) | ...	| ... , nD |
| BIPUSH b, −128 _ b < 127 |	... |	... , b |
| SIPUSH s, −32768 _ s < 32767 |	... |	... , s |
| LDC cst (int, float, long, double, String 或 Type) |	... |	... , cst |
| ACONST_NULL |	... |	... , null |

>算数与逻辑

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	IADD, LADD, FADD, DADD	|	... , a , b	|	... , a + b	|
|	ISUB, LSUB, FSUB, DSUB	|	... , a , b	|	... , a - b	|
|	IMUL, LMUL, FMUL, DMUL	|	... , a , b	|	... , a * b	|
|	IDIV, LDIV, FDIV, DDIV	|	... , a , b	|	... , a / b	|
|	IREM, LREM, FREM, DREM	|	... , a , b	|	... , a % b	|
|	INEG, LNEG, FNEG, DNEG	|	... , a	|	... , -a	|
|	ISHL, LSHL	|	... , a , n	|	... , a <_< n	|
|	ISHR, LSHR	|	... , a , n	|	... , a >_> n	|
|	IUSHR, LUSHR	|	... , a , n	|	... , a >_>_> n	|
|	IAND, LAND	|	... , a , b	|	... , a & b	|
|	IOR, LOR	|	... , a , b	|	... , a | b	|
|	IXOR, LXOR	|	... , a , b	|	... , a ^ b	|
|	LCMP	|	... , a , b	|	... , a == b ? 0 : (a < b ? -1 : 1)	|
|	FCMPL, FCMPG	|	... , a , b	|	... , a == b ? 0 : (a < b ? -1 : 1)	|
|	DCMPL, DCMPG	|	... , a , b	|	... , a == b ? 0 : (a < b ? -1 : 1)	|

>类型转换

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	I2B	|	... , i	|	... , (byte) i
|	I2C	|	... , i	|	... , (char) i
|	I2S	|	... , i	|	... , (short) i
|	L2I, F2I, D2I	|	... , a	|	... , (int) a
|	I2L, F2L, D2L	|	... , a	|	... , (long) a
|	I2F, L2F, D2F	|	... , a	|	... , (float) a
|	I2D, L2D, F2D	|	... , a	|	... , (double) a
|	CHECKCAST class	|	... , o	|	... , (class) o

>对象、字段和方法

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	NEW class …	|	…,new class	|		|
|	GETFIELD c f t	|	... , o	|	... , o.f	|
|	PUTFIELD c f t	|	... , o , v	|	...	|
|	GETSTATIC c f t	|	...	|	... , c.f	|
|	PUTSTATIC c f t	|	... , v	|	...	|
|	INVOKEVIRTUAL c m t	|	... , o , v1 , ... , vn	|	... , o.m(v1, ... vn)	|
|	INVOKESPECIAL c m t	|	... , o , v1 , ... , vn	|	... , o.m(v1, ... vn)	|
|	INVOKESTATIC c m t	|	... , v1 , ... , vn	|	... , c.m(v1, ... vn)	|
|	INVOKEINTERFACE c m t	|	... , o , v1 , ... , vn	|	... , o.m(v1, ... vn)	|
|	INVOKEDYNAMIC m t bsm	|	... , o , v1 , ... , vn	|	... , o.m(v1, ... vn)	|
|	INSTANCEOF class	|	... , o	|	... , o instanceof class	|
|	MONITORENTER	|	... , o	|	...	|
|	MONITOREXIT	|	... , o	|	...	|

>数组

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	NEWARRAY type （用于任意基元类型）	|	... , n	|	... , new type[n]	|
|	ANEWARRAY class	|	... , n	|	... , new class[n]	|
|	MULTIANEWARRAY [...[t n	|	... , i1 ,... , in	|	... , new t[i1]...[in]…	|
|	BALOAD, CALOAD, SALOAD	|	... , o , i	|	... , o[i]	|
|	IALOAD, LALOAD,FALOAD, DALOAD	|	... , o , i	|	... , o[i]	|
|	AALOAD	|	... , o , i	|	... , o[i]	|
|	BASTORE, CASTORE, SASTORE	|	... , o , i , j	|	...	|
|	IASTORE, LASTORE, FASTORE, DASTORE	|	... , o , i , a	|	...	|
|	AASTORE	|	... , o , i , p	|	...	|
|	ARRAYLENGTH	|	... , o	|	... , o.length	|

>跳转

| 指令 | 之前的栈 | 之后的栈 |
|:---|:---|:---|
|	IFEQ	|	... , i	|	... i == 0 时跳转	|
|	IFNE	|	... , i	|	... i != 0 时跳转	|
|	IFLT	|	... , i	|	... i < 0 时跳转	|
|	IFGE	|	... , i	|	... i >= 0 时跳转	|
|	IFGT	|	... , i	|	... i > 0 时跳转	|
|	IFLE	|	... , i	|	... i <= 0 时跳转	|
|	IF_ICMPEQ	|	... , i , j	|	... i == j 时跳转	|
|	IF_ICMPNE	|	... , i , j	|	... i != j 时跳转	|
|	IF_ICMPLT	|	... , i , j	|	... i < j 时跳转	|
|	IF_ICMPGE	|	... , i , j	|	... i >= j 时跳转	|
|	IF_ICMPGT	|	... , i , j	|	... i > j 时跳转	|
|	IF_ICMPLE	|	... , i , j	|	... i <= j 时跳转	|
|	IF_ACMPEQ	|	... , o , p	|	... o == p 时跳转	|
|	IF_ACMPNE	|	... , o , p	|	... o != p 时跳转	|
|	IFNULL	|	... , o	|	... o == null 时跳转	|
|	IFNONNULL	|	... , o	|	... o != null 时跳转	|
|	GOTO	|	...	|	... 总是跳转	|
|	TABLESWITCH	|	... , i	|	... 总是跳转	|
|	LOOKUPSWITCH	|	... , i	|	... 总是跳转	|

>返回

| 指令 | 之前的栈 |
|:---|:---|
| IRETURN, LRETURN, FRETURN, DRETURN |	...	,	a |
| ARETURN	 | ...	,	o |
| RETURN	 | ... |
| ATHROW	 | ...	,	o |
