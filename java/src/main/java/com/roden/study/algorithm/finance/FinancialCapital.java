package com.roden.study.algorithm.finance;

/**
 * @author luod
 */
public class FinancialCapital {
        private double amount;
        private double principal;
        private double interest;
        private int period;

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        public double getPrincipal() {
                return principal;
        }

        public void setPrincipal(double principal) {
                this.principal = principal;
        }

        public double getInterest() {
                return interest;
        }

        public void setInterest(double interest) {
                this.interest = interest;
        }

        public int getPeriod() {
                return period;
        }

        public void setPeriod(int period) {
                this.period = period;
        }

        @Override
        public String toString() {
                return "FinancialCapital{" +
                        "amount=" + amount +
                        ", principal=" + principal +
                        ", interest=" + interest +
                        ", period=" + period +
                        '}';
        }
}