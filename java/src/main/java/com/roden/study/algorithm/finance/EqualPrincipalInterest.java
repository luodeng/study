package com.roden.study.algorithm.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。把按揭贷款的本金总额与利息总额相加，
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 *
 * 每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
 * 每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
 *
 *
 *  https://www.rong360.com/baike/1.html
 *  　　等额本息法最重要的一个特点是每月的还款额相同，从本质上来说是本金所占比例逐月递增，利息所占比例逐月递减，月还款数不变，
 *  即在月供“本金与利息”的分配比例中，前半段时期所还的利息比例大、本金比例小，还款期限过半后逐步转为本金比例大、利息比例小，
 *  其计算公式为：
 *  　　	每月还本付息金额 =[ 本金 x 月利率 x(1+月利率)贷款月数 ] / [(1+月利率)还款月数 - 1]			　　
 *  　　	还款总利息=贷款额*贷款月数*月利率*（1+月利率）贷款月数/【（1+月利率）还款月数 - 1】-贷款额
 *  　　	还款总额=  还款月数*贷款额*月利率*（1+月利率）贷款月数/【（1+月利率）还款月数 - 1】
 *  　　	每月利息 = 剩余本金x贷款月利率
 *  　　	每月本金=月供-每月利息
 *
 *  　　注意：在等额本息法中，银行一般先收剩余本金利息，后收本金，所以利息在月供款中的比例会随本金的减少而降低，
 *  本金在月供款中的比例因而升高，但月供总额保持不变。
 *
 *  　　例如，100万元，30年基准利率为例，经计算器测算，每月还款6092.28元，总支付利息是1193221.37元。
 *  第一个月利息为1000000*（6.15%/12）=5125元，第一个月本金为6092.28-5125=967.28元。
 *  第二个月利息为（1000000-967.28）*（6.15%/12）=5585.5011元，本金为6092.28-5585.5011=506.778元。以此计算。
 *
 *  @author luod
 */
public class EqualPrincipalInterest {
    public static List<FinancialCapital> calc(double loanAmount, int period, double yearRate){
        return calc(BigDecimal.valueOf(loanAmount),period,BigDecimal.valueOf(yearRate));
    }

   public static List<FinancialCapital> calc(BigDecimal loanAmount, int period, BigDecimal yearRate){
       List<FinancialCapital> financialCapitalList = new ArrayList<>(period);
       //月还款金额
       BigDecimal monthRate=yearRate.divide(BigDecimal.valueOf(12),16,BigDecimal.ROUND_HALF_UP);
       if(monthRate.doubleValue()==0){
           monthRate=BigDecimal.valueOf(0.0000000000000001);
       }
       BigDecimal amount=loanAmount.multiply(monthRate).multiply(BigDecimal.ONE.add(monthRate).pow(period)).divide(BigDecimal.ONE.add(monthRate).pow(period).subtract(BigDecimal.ONE),16,BigDecimal.ROUND_HALF_UP);
       //剩余未还本金
       BigDecimal remainPrincipal=loanAmount;
       for(int i = 1;i <= period ; i++ ){
           FinancialCapital financialCapital =new FinancialCapital();
           financialCapital.setAmount(amount.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());

           //月还款利息
           BigDecimal interest=remainPrincipal.multiply(monthRate);
           //月还款本金
           BigDecimal principal=amount.subtract(interest).setScale(2,BigDecimal.ROUND_HALF_UP);

           //确保 本金之和等于货款金额
           if(i==period){
               financialCapital.setPrincipal(remainPrincipal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
           }else{
               financialCapital.setPrincipal(principal.doubleValue());
           }
           remainPrincipal=remainPrincipal.subtract(principal);

           //确保  本金+利息=总金额
           financialCapital.setInterest(BigDecimal.valueOf(financialCapital.getAmount()).subtract(BigDecimal.valueOf(financialCapital.getPrincipal())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
           //确保利率为0时，利息不为负数
           if(financialCapital.getInterest()<0){
               financialCapital.setInterest(0);
               financialCapital.setAmount(financialCapital.getPrincipal()+financialCapital.getInterest());
           }
           financialCapital.setPeriod(i);
           financialCapitalList.add(financialCapital);
       }
        return financialCapitalList;
   }
}
