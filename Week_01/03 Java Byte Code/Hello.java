class Hello {
	public static void main(String[] args) {
		int a = 100;	// binpush 100  istore_1
		int b = 2;	// iconst_2  istore_2
		double c = 10D;	// ldc2_w  dstore_3
		int d = a + b;	// iload_1 iload_2 iadd istore 
		double e = a - c;	// iload_1 i2d dload_3 dsub dstore
		int f = a * b;		// iload_1 iload_2 imul istore
		double g = a / c;		// iload_1 i2d dload_3 ddiv dstore
				
		if(a == b) {		// iload_1 iload_2  if_icmpne  
			System.out.println("a==b");
		}else {
			System.out.println("a!=b");	// getstatic ldc invokevirtual
		}

		for(int i = 0; i < b; i++) {	// iconst_0 istore iload iload_2 if_icmpge iinc
			System.out.println("第" + i + "次");	// getstatic ldc invokevirtual
		}
	}
}
