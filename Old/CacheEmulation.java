import java.io.*;
import java.util.*;
import java.util.function.Predicate;

class LRU {

    static void setPriorityLRU(int[] arr, int x){
        for (int i=0;i<arr.length;i++){
            if(i!=x){
                arr[i]++;
            }
            else{
                arr[i]=0;
            }
        }
    }

    static int getHighestPriorityIndex(int[] arr){
        int l=-100;
        int x=0;
        for (int i=0;i<arr.length;i++){
            int lOld=l;
            l=Math.max(l,arr[i]);
            if(lOld!=l){
                x=i;
            }
        }
        return x;
    }

    public static void main(String[] args) throws IOException {

        String addressSequenceFilePath = ".\\" + args[0];
        int cacheSize = Integer.parseInt(args[1]);

        File addressSequenceData=new File(addressSequenceFilePath);
        Scanner sc=new Scanner(addressSequenceData);

        //Declaring arraylist to store entries
        ArrayList<String> temporaryList = new ArrayList<>();

        while(sc.hasNextLine()){
            temporaryList.add(sc.nextLine());
        }

        //Breaking every element in the arraylist to get distinct entries from the same line

        ArrayList<String> entries = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (int i=0;i<temporaryList.size();i++){
            String line=temporaryList.get(i);
            for (int j=0;j<line.length();j++){
                if (line.charAt(j)!=' '){
                    temp.append(line.charAt(j));
                }
                else {
                     entries.add(temp.toString().trim());
                     temp.delete(0, temp.length());
                }
            }
            entries.add(temp.toString().trim());
            temp.delete(0, temp.length());
        }

        //Removing any residual empty lines
        Predicate<String> newLinePresent = a->(a.equals("\n")||a.equals("\r")||a.equals("\r\n")||a.equals(""));
        entries.removeIf(newLinePresent);

        sc.close();

        String[] hitMiss= new String[entries.size()];

        String[] cache = new String[cacheSize];

        //LRU Policy implementation

        int totalMisses=0;

        //Calculating compulsory misses i.e., number of distinct entries

        int distinctEntries = 1;

        for (int i=1;i<entries.size();i++){
            int j;
            for (j=0;j<i;j++)
                if (entries.get(i).equals(entries.get(j)))
                    break;
            if (i==j)
                distinctEntries++;
        }


        int[] priorityFlags = new int[cacheSize];
        for(int i=0;i<cacheSize;i++){
            priorityFlags[i]=0;
        }

        L:for (int i=0;i<entries.size();i++){
            //Checking if already present in cache
            for(int j=0;j<cache.length;j++){
                if(entries.get(i).equals(cache[j])){
                    setPriorityLRU(priorityFlags,j);
                    hitMiss[i]="HIT";
                    continue L;
                }
            }
            //If not present
            totalMisses++;
            hitMiss[i]="MISS";
            int c=getHighestPriorityIndex(priorityFlags);
            cache[c]=entries.get(i);
            setPriorityLRU(priorityFlags,c);
        }


        //Removing .txt
        StringBuilder withoutTXT = new StringBuilder(args[0]);
        withoutTXT.delete(args[0].length()-4,args[0].length());
        String withoutTXT1=withoutTXT.toString();

        //Output

        String outputFileName = "21116058_LRU_" + withoutTXT1 + "_" + args[1] + ".out";

        //Writing data in the output file

        FileWriter writer = new FileWriter(".\\" + outputFileName);
        StringBuilder content = new StringBuilder();

        content.append("TOTAL_ACCESSES = ").append(entries.size()).append("\n");
        content.append("TOTAL_MISSES = ").append(totalMisses).append("\n");
        content.append("COMPULSORY_MISSES = ").append(distinctEntries).append("\n");
        content.append("CAPACITY_MISSES = ").append(totalMisses-distinctEntries);

        for (String miss : hitMiss) {
            content.append("\n").append(miss);
        }
        writer.write(content.toString());
        writer.close();
    }
}

class OPTIMAL{

    static void setPriorityOPTIMAL(int[] cache,int[] priorityFlags,int[] entries,int currentIndex){
        if(currentIndex!=entries.length) {
            for (int i = 0; i < cache.length; i++) {
                int flag = entries.length;
                for (int j = currentIndex + 1; j < entries.length; j++) {
                    if (cache[i]==entries[j]) {
                        flag = j - currentIndex;
                        break;
                    }
                }
                priorityFlags[i] = flag;
            }
        }
    }

    static int getHighestPriorityIndex(int[] arr){
        int l=-100;
        int x=0;
        for (int i=0;i<arr.length;i++){
            int lOld=l;
            l=Math.max(l,arr[i]);
            if(lOld!=l){
                x=i;
            }
        }
        return x;
    }

    public static void main(String[] args) throws IOException{

        String addressSequenceFilePath = ".\\" + args[0];
        int cacheSize = Integer.parseInt(args[1]);

        File addressSequenceData=new File(addressSequenceFilePath);
        Scanner sc=new Scanner(addressSequenceData);

        //Declaring arraylist to store entries
        ArrayList<String> temporaryList = new ArrayList<>();

        while(sc.hasNextLine()){
            temporaryList.add(sc.nextLine());
        }

        //Breaking every element in the arraylist to get distinct entries from the same line

        ArrayList<String> entriesArrayList = new ArrayList<>();
        StringBuilder temp = new StringBuilder();
        for (int i=0;i<temporaryList.size();i++){
            String line=temporaryList.get(i);
            for (int j=0;j<line.length();j++){
                if (line.charAt(j)!=' '){
                    temp.append(line.charAt(j));
                }
                else {
                    entriesArrayList.add(temp.toString().trim());
                    temp.delete(0, temp.length());
                }
            }
            entriesArrayList.add(temp.toString().trim());
            temp.delete(0, temp.length());
        }

        //Removing any residual empty lines
        Predicate<String> newLinePresent = a->(a.equals("\n")||a.equals("\r")||a.equals("\r\n")||a.equals(""));
        entriesArrayList.removeIf(newLinePresent);

        sc.close();

        //Converting Arraylist to integer array
        int[] entries = new int[entriesArrayList.size()];

        //Algorithm to get distinct integers if alphabetical entries are given
        StringBuilder s=new StringBuilder();
        for (int j=0;j<entriesArrayList.size();j++){
            for (int k=0;k<entriesArrayList.get(j).length();k++){
                int y=entriesArrayList.get(j).charAt(k)-48;
                s.append(y);
            }
            entries[j]= Integer.parseInt(s.toString());
            s.delete(0, s.length());
        }

        String[] hitMiss= new String[entries.length];

        int[] cache = new int[cacheSize];

        int totalMisses=0;

        //Calculating compulsory misses i.e., number of distinct entries

        int distinctEntries = 1;

        for (int i=1;i<entries.length;i++){
            int j;
            for (j=0;j<i;j++)
                if (entries[i]==(entries[j]))
                    break;
            if (i==j)
                distinctEntries++;
        }

        int[] priorityFlags = new int[cacheSize];
        for(int i=0;i<cacheSize;i++){
            priorityFlags[i]= entries.length;
        }

        //Implementing OPTIMAL

        L:for (int i=0;i<entries.length;i++){
            //Checking if already present in cache
            for (int k : cache) {
                if (entries[i] == k) {
                    setPriorityOPTIMAL(cache, priorityFlags, entries, i);
                    hitMiss[i] = "HIT";
                    continue L;
                }
            }
            //If not present
            totalMisses++;
            hitMiss[i]="MISS";
            int c=getHighestPriorityIndex(priorityFlags);
            cache[c]=entries[i];
            setPriorityOPTIMAL(cache,priorityFlags,entries,i);
        }

        //Removing .txt
        StringBuilder withoutTXT = new StringBuilder(args[0]);
        withoutTXT.delete(args[0].length()-4,args[0].length());
        String withoutTXT1=withoutTXT.toString();

        //Output

        String outputFileName = "21116058_OPTIMAL_" + withoutTXT1 + "_" + args[1] + ".out";

        //Writing data in the output file

        FileWriter writer = new FileWriter(".\\" + outputFileName);
        StringBuilder content = new StringBuilder();

        content.append("TOTAL_ACCESSES = ").append(entries.length).append("\n");
        content.append("TOTAL_MISSES = ").append(totalMisses).append("\n");
        content.append("COMPULSORY_MISSES = ").append(distinctEntries).append("\n");
        content.append("CAPACITY_MISSES = ").append(totalMisses-distinctEntries);

        for (String miss : hitMiss) {
            content.append("\n").append(miss);
        }
        writer.write(content.toString());
        writer.close();
    }
}