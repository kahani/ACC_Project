package SearchFunctionality;


public class EditDistance {
	
	
	public static int calcuateEditDistance(String str1, String str2) {
		int strLength1 = str1.length();
		int strLength2 = str2.length();
	 
		int[][] dist = new int[strLength1 + 1][strLength2 + 1];
	 
		for (int i = 0; i <= strLength1; i++) 
			dist[i][0] = i;
		
	 
		for (int j = 0; j <= strLength2; j++) 
			dist[0][j] = j;
		
	 
		for (int i = 0; i < strLength1; i++) 
		{
			char c1 = str1.charAt(i);
			for (int j = 0; j < strLength2; j++) 
			{
				char c2 = str2.charAt(j);
	 
				//if last two chars  are equal
				if (c1 == c2) 
				{
					//update dp value for +1 length
					dist[i + 1][j + 1] = dist[i][j];
				} 
				else 
				{
					int replace = dist[i][j] + 1;
					int insert = dist[i][j + 1] + 1;
					int delete = dist[i + 1][j] + 1;
					int minimum =0;
					if(replace>insert)
						minimum = insert;
					else
						minimum = replace;
					
					if(delete<minimum)
						minimum = delete;
					
					dist[i + 1][j + 1] = minimum;
				}
			}
		}
	 
		return dist[strLength1][strLength2];
	}
	
}
