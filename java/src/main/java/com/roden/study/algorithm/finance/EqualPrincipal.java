package com.roden.study.algorithm.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 等额本金
 * @author luod
 */
public class EqualPrincipal {
    public static List<FinancialCapital> calc(double loanAmount, int period, double yearRate){
        return calc(BigDecimal.valueOf(loanAmount),period,BigDecimal.valueOf(yearRate));
    }

   public static List<FinancialCapital> calc(BigDecimal loanAmount, int period, BigDecimal yearRate){
       List<FinancialCapital> financialCapitalList = new ArrayList<>(period);
       //月利率
       BigDecimal monthRate=yearRate.divide(BigDecimal.valueOf(12),16,BigDecimal.ROUND_HALF_UP);
       if(monthRate.doubleValue()==0){
           monthRate=BigDecimal.valueOf(0.0000000000000001);
       }
       //剩余未还本金
       BigDecimal remainPrincipal=loanAmount;
       //月还款本金
       BigDecimal principal=loanAmount.divide(BigDecimal.valueOf(period),2,BigDecimal.ROUND_DOWN);
       for(int i = 1;i <= period ; i++ ){
           FinancialCapital financialCapital =new FinancialCapital();
           //月还款利息
           BigDecimal interest=remainPrincipal.multiply(monthRate).setScale(2,BigDecimal.ROUND_UP);
           financialCapital.setInterest(interest.doubleValue());
           //确保 本金之和等于货款金额
           if(i==period){
               financialCapital.setPrincipal(remainPrincipal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
           }else{
               financialCapital.setPrincipal(principal.doubleValue());
           }
           remainPrincipal=remainPrincipal.subtract(principal);
           financialCapital.setAmount(interest.add(BigDecimal.valueOf(financialCapital.getPrincipal())).doubleValue());
           financialCapital.setPeriod(i);
           financialCapitalList.add(financialCapital);
       }
        return financialCapitalList;
   }
}
