package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Main {
    Map<String,Coin> coinMap = new TreeMap<String, Coin>(Collections.reverseOrder());
    BigDecimal smallestCoin;
    public static void main(String[] args) {

        //BigDecimal amount = new BigDecimal("112.51999125");

        Main main = new Main();
        main.addCoin("0.75");
        main.addCoin("0.15");
        main.addCoin("0.03");
        main.addCoin("0.01");
        main.addCoin("0.005");
        main.addCoin("0.0025");
        //System.out.print(main.change(amount));
        int percussion = 0;
        Random r = new Random();
        int max = 2147483647;
        for(int i = 0; i<max; i++) {
            double rangeMin = 0.0;
            double rangeMax = 1000000;

            double ramdon = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            BigDecimal amount = new BigDecimal(ramdon);


            Boolean correct = main.verify(main.change(amount), amount);
            //System.out.println(correct);

            if(correct)
                percussion++;
        }
        System.out.println(percussion/max);
    }
    public boolean verify(Map<String,Integer> changeMap,BigDecimal amount){
        BigDecimal total = new BigDecimal(0);
        for(Map.Entry<String,Integer> coin : changeMap.entrySet()) {
            BigDecimal amountOfCoins = new BigDecimal(coin.getValue().toString());
            total = total.add(coinMap.get(coin.getKey()).getValue().multiply(amountOfCoins));
        }
        if(total.compareTo(smallestCoin)==1)
            return true;
        else
            return false;
    }
    public void addCoin(String value){
        Coin coin = new Coin(value);
        coinMap.put(value,coin);
        if(smallestCoin==null || coin.getValue().compareTo(smallestCoin)==1){
            smallestCoin = coin.getValue();
        }
    }


    public Map<String,Integer> change(BigDecimal amount){
        Map<String,Integer> changeMap = new TreeMap<String, Integer>(Collections.reverseOrder());
        for(Map.Entry<String,Coin> coinEntry : coinMap.entrySet()) {

            String key = coinEntry.getKey();
            Coin coin = coinEntry.getValue();
            int amountOfCoins = amount.divide(coin.getValue(), RoundingMode.DOWN).setScale(0, RoundingMode.DOWN).intValue();
            changeMap.put(key,amountOfCoins);
            amount = amount.remainder(coin.getValue());

        }

        /*
        if(!amount.equals(0))
            System.out.println("Warning not all money could be converted. The amount left to convert: "+ amount.toString());
        */
        return changeMap;

    }

}
class Coin{
    private final BigDecimal value;
    private final String name;
    public Coin(String amount){
        value = new BigDecimal(amount);
        name= amount;
    }
    public BigDecimal getValue(){return value;}
    public String toString(){return value.toString();}
}

