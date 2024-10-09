import java.io.*;
import java.util.*;

class LRU {

    static void setPriorityLRU(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (i != x) {
                arr[i]++;
            } else {
                arr[i] = 0;
            }
        }
    }

    static int getHighestPriorityIndex(int[] arr) {
        int l = -100;
        int x = 0;
        for (int i = 0; i < arr.length; i++) {
            int lOld = l;
            l = Math.max(l, arr[i]);
            if (lOld != l) {
                x = i;
            }
        }
        return x;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: java LRU <cache size> <address1> <address2> ... <addressN>");
            System.exit(1);
        }

        int cacheSize = Integer.parseInt(args[0]);
        List<String> entries = new ArrayList<>();

        // Add addresses to the list from command line arguments
        for (int i = 1; i < args.length; i++) {
            entries.add(args[i]);
        }

        String[] hitMiss = new String[entries.size()];
        String[] cache = new String[cacheSize];

        // LRU Policy implementation
        int totalMisses = 0;
        int distinctEntries = 0;

        Set<String> uniqueEntries = new HashSet<>();

        int[] priorityFlags = new int[cacheSize];
        Arrays.fill(priorityFlags, 0);

        for (int i = 0; i < entries.size(); i++) {
            // Check if already present in cache
            boolean hit = false;
            for (int j = 0; j < cache.length; j++) {
                if (entries.get(i).equals(cache[j])) {
                    setPriorityLRU(priorityFlags, j);
                    hitMiss[i] = "HIT";
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                // Not present
                totalMisses++;
                hitMiss[i] = "MISS";
                int c = getHighestPriorityIndex(priorityFlags);
                cache[c] = entries.get(i);
                setPriorityLRU(priorityFlags, c);

                // Track distinct entries
                uniqueEntries.add(entries.get(i));
            }
        }

        // Compulsory misses
        distinctEntries = uniqueEntries.size();

        // Output
        String outputFileName = "21116058_LRU_output.out";
        FileWriter writer = new FileWriter(outputFileName);
        StringBuilder content = new StringBuilder();
        
        content.append("TOTAL_ACCESSES = ").append(entries.size()).append("\n");
        content.append("TOTAL_MISSES = ").append(totalMisses).append("\n");
        content.append("COMPULSORY_MISSES = ").append(distinctEntries).append("\n");
        content.append("CAPACITY_MISSES = ").append(totalMisses - distinctEntries).append("\n");

        for (String miss : hitMiss) {
            content.append(miss).append("\n");
        }
        writer.write(content.toString());
        writer.close();
    }
}

class OPTIMAL {

    static void setPriorityOPTIMAL(int[] cache, int[] priorityFlags, int[] entries, int currentIndex) {
        if (currentIndex != entries.length) {
            for (int i = 0; i < cache.length; i++) {
                int flag = entries.length;
                for (int j = currentIndex + 1; j < entries.length; j++) {
                    if (cache[i] == entries[j]) {
                        flag = j - currentIndex;
                        break;
                    }
                }
                priorityFlags[i] = flag;
            }
        }
    }

    static int getHighestPriorityIndex(int[] arr) {
        int l = -100;
        int x = 0;
        for (int i = 0; i < arr.length; i++) {
            int lOld = l;
            l = Math.max(l, arr[i]);
            if (lOld != l) {
                x = i;
            }
        }
        return x;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: java OPTIMAL <cache size> <address1> <address2> ... <addressN>");
            System.exit(1);
        }

        int cacheSize = Integer.parseInt(args[0]);
        List<Integer> entries = new ArrayList<>();

        // Add addresses to the list from command line arguments
        for (int i = 1; i < args.length; i++) {
            entries.add(Integer.parseInt(args[i]));
        }

        String[] hitMiss = new String[entries.size()];
        int[] cache = new int[cacheSize];
        int totalMisses = 0;
        int distinctEntries = 0;

        Set<Integer> uniqueEntries = new HashSet<>();

        int[] priorityFlags = new int[cacheSize];
        Arrays.fill(priorityFlags, entries.size());

        // Implementing OPTIMAL
        for (int i = 0; i < entries.size(); i++) {
            boolean hit = false;
            for (int k : cache) {
                if (entries.get(i) == k) {
                    setPriorityOPTIMAL(cache, priorityFlags, entries.stream().mapToInt(Integer::intValue).toArray(), i);
                    hitMiss[i] = "HIT";
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                // Not present
                totalMisses++;
                hitMiss[i] = "MISS";
                int c = getHighestPriorityIndex(priorityFlags);
                cache[c] = entries.get(i);
                setPriorityOPTIMAL(cache, priorityFlags, entries.stream().mapToInt(Integer::intValue).toArray(), i);

                // Track distinct entries
                uniqueEntries.add(entries.get(i));
            }
        }

        // Compulsory misses
        distinctEntries = uniqueEntries.size();

        // Output
        String outputFileName = "21116058_OPTIMAL_output.out";
        FileWriter writer = new FileWriter(outputFileName);
        StringBuilder content = new StringBuilder();
        
        content.append("TOTAL_ACCESSES = ").append(entries.size()).append("\n");
        content.append("TOTAL_MISSES = ").append(totalMisses).append("\n");
        content.append("COMPULSORY_MISSES = ").append(distinctEntries).append("\n");
        content.append("CAPACITY_MISSES = ").append(totalMisses - distinctEntries).append("\n");

        for (String miss : hitMiss) {
            content.append(miss).append("\n");
        }
        writer.write(content.toString());
        writer.close();
    }
}
