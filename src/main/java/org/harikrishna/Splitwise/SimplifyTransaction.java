package org.harikrishna.Splitwise;

import java.util.*;

class Pair {
    public Integer user;
    public Integer netAmount;
}

class PairMinComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        if (o1.netAmount > o2.netAmount) return 1;
        else if (o1.netAmount < o2.netAmount) return -1;
        return 0;
    }
}

class PairMaxComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        if (o1.netAmount < o2.netAmount) return 1;
        else if (o1.netAmount > o2.netAmount) return -1;
        return 0;
    }
}


public class SimplifyTransaction {

    public int minTransfers(int[][] transactions) {

        //Compute each user netAmount (Incoming - Outgoing)
        Map<Integer, Integer> userToNetAmount = new HashMap<>();
        for (int[] txn : transactions) {
            int from = txn[0];
            int to = txn[1];
            int amount = txn[2];
            userToNetAmount.put(from, userToNetAmount.getOrDefault(from, 0) - amount);
            userToNetAmount.put(to, userToNetAmount.getOrDefault(to, 0) + amount);
        }

        //Divide receivers and givers into respective groups with max and min priority queue.
        PriorityQueue<Pair> receivers = new PriorityQueue<>(new PairMaxComparator());
        PriorityQueue<Pair> givers = new PriorityQueue<>(new PairMinComparator());
        for (Map.Entry<Integer, Integer> item : userToNetAmount.entrySet()) {
            Pair pair = new Pair();
            pair.user = item.getKey();
            pair.netAmount = item.getValue();
            //If the net amount is Zero, then that user doesn't need to pay or receive anything
            if (item.getValue() != 0) {
                if (item.getValue() < 0) {
                    givers.add(pair);
                } else {
                    receivers.add(pair);
                }
            }
        }

        //Find no of transfers required and transfers data
        return findMinTransBtwUsers(receivers, givers);
    }

    public int findMinTransBtwUsers(PriorityQueue<Pair> receivers, PriorityQueue<Pair> givers) {
        int tns = 0;
        StringBuilder stringBuilder = new StringBuilder();

        //Calculate till someone has to receive the money.
        //Basically if someone has to receive the money then someone has to give the money and vice-versa
        while(!receivers.isEmpty()) {
            Pair maxPair = receivers.poll();
            Pair minPair = givers.poll();
            if ((maxPair.netAmount + minPair.netAmount) < 0) {
                /*
                If giver amount > receiver amount then giver still has some amount to give.
                So we calculate remaining amount and add the giver back to givers queue.
                */
                stringBuilder.append(minPair.user + "->" + maxPair.user + ": " + maxPair.netAmount + "\n");
                Pair pair = new Pair();
                pair.user = minPair.user;
                pair.netAmount = maxPair.netAmount + minPair.netAmount;
                givers.add(pair);
            } else if ((maxPair.netAmount + minPair.netAmount) > 0) {
                /*
                If giver amount < receiver amount then receiver still has some amount to get.
                So we calculate remaining amount and add the receiver back to receivers queue.
                */
                stringBuilder.append(minPair.user + "->" + maxPair.user + ": " + Math.abs(minPair.netAmount) + "\n");
                Pair pair = new Pair();
                pair.user = maxPair.user;
                pair.netAmount = maxPair.netAmount + minPair.netAmount;
                receivers.add(pair);
            } //If giver amount == receiver amount then just one transaction.
            else {
                stringBuilder.append(minPair.user + "->" + maxPair.user + ": " + maxPair.netAmount + "\n");
            }
            tns++;
        }
        System.out.println(stringBuilder);
        return tns;
    }

    public static void main(String[] args) {

        SimplifyTransaction simplifyTransaction = new SimplifyTransaction();
        //{fromUser, toUser, amount}
        int[][] txns = {{0, 1, 10}, {1, 2, 3}, {2, 3, 5}, {3, 2, 28}, {1, 3, 17}, {2, 1, 8}, {3, 1, 5}};

        int output = simplifyTransaction.minTransfers(txns);
        System.out.println("No of transfers:" + output);
    }

//    public static void main(String[] args) {
//        PriorityQueue<Pair> toMaxHeap = new PriorityQueue<>(new PairMaxComparator());
//        PriorityQueue<Pair> fromMinHeap = new PriorityQueue<>(new PairMinComparator());
//
//        int[][] input = {{1,50}, {2, 100}, {3, 50}, {4, -75}, {5, -75}, {6, -50}};
//        for (int[] txn: input) {
//            Pair pair = new Pair();
//            pair.user = txn[0];
//            pair.netAmount = txn[1];
//            if (pair.netAmount != 0) {
//                if (pair.netAmount < 0) {
//                    fromMinHeap.add(pair);
//                } else {
//                    toMaxHeap.add(pair);
//                }
//            }
//        }
//
//        SimplifyTransaction simplifyTransaction = new SimplifyTransaction();
//        simplifyTransaction.findMinTransBtwUsers(toMaxHeap, fromMinHeap);
//    }
}

