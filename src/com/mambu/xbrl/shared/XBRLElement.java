package com.mambu.xbrl.shared;

import static com.mambu.xbrl.shared.ElementCategory.*;
import static com.mambu.xbrl.shared.ElementType.*;
import static com.mambu.xbrl.shared.Namespace.*;
import static com.mambu.xbrl.shared.PeriodType.*;

import java.io.Serializable;
/**
 * The XBRL Elements which can be added and generated
 * 
 * @author edanilkis
 * 
 */
public enum XBRLElement implements Serializable {

	//Balance Sheet
	TOTAL_ASSETS (IFRS, "TotalAssets", MONEY, INSTANT, BALANCE_SHEET),
	CASH_AND_CASH_EQUIVALENTS (IFRS, "CashAndCashEquivalents", MONEY, INSTANT, BALANCE_SHEET),
	BALANCES_WITH_BANKS (IFRS, "BalancesWithBanks", MONEY, INSTANT, BALANCE_SHEET),
	CASH_ON_HAND (IFRS, "CashOnHand", MONEY, INSTANT, BALANCE_SHEET),
	COMPREHENSIVE_INCOME_RETAINED_EARNINGS (IFRS, "ComprehensiveIncomeRetainedEarnings", MONEY, DURATION, BALANCE_SHEET),
	CURRENT_TAX_LIABILITIES (IFRS, "CurrentTaxLiabilities", MONEY, INSTANT, BALANCE_SHEET),
	DEFERRED_TAX_LIABILITIES (IFRS, "DeferredTaxLiabilities", MONEY, INSTANT, BALANCE_SHEET),
	EQUITY(IFRS, "Equity", MONEY, INSTANT, BALANCE_SHEET),
	EQUITY_AND_LIABILITIES (IFRS, "EquityAndLiabilities", MONEY, INSTANT, BALANCE_SHEET),
	ISSUED_CAPITAL (IFRS, "IssuedCapital", MONEY, INSTANT, BALANCE_SHEET),
	LIABILITIES (IFRS, "Liabilities", MONEY, INSTANT, BALANCE_SHEET),
	OTHER_EQUITY_INTEREST (IFRS, "OtherEquityInterest", MONEY, INSTANT, BALANCE_SHEET),
	OTHER_RESERVES (IFRS, "OtherReserves", MONEY, INSTANT, BALANCE_SHEET),
	Profit_Loss (IFRS, "ProfitLoss", MONEY, INSTANT, BALANCE_SHEET),
	PROPERTY_PLANT_AND_EQUIPMENT (IFRS, "PropertyPlantAndEquipment", MONEY, INSTANT, BALANCE_SHEET),
	RETAINED_EARNINGS (IFRS, "RetainedEarnings", MONEY, INSTANT, BALANCE_SHEET),
	SHORT_TERM_INVESTMENTS_CLASSIFIED_AS_CASH_EQUIVALENTS (IFRS, "ShortTermInvestmentsClassifiedAsCashEquivalents", MONEY, INSTANT, BALANCE_SHEET),
	TRADE_AND_OTHER_PAYABLES (IFRS, "TradeAndOtherPayables", MONEY, INSTANT, BALANCE_SHEET),
	TRADE_AND_OTHER_RECEIVABLES (IFRS, "TradeAndOtherReceivables", MONEY, INSTANT, BALANCE_SHEET),
	ACCOUNTS_PAYABLE (MIX, "AccountsPayable", MONEY, INSTANT, BALANCE_SHEET),
	BORROWINGS (MIX, "Borrowings", MONEY, INSTANT, BALANCE_SHEET),
	DEFERRED_REVENUE (MIX, "DeferredRevenue", MONEY, INSTANT, BALANCE_SHEET),
	DEPOSITS (MIX, "Deposits", MONEY, INSTANT, BALANCE_SHEET),
	DONATED_EQUITY (MIX, "DonatedEquity", MONEY, INSTANT, BALANCE_SHEET),
	IMPAIRMENT_LOSS_ALLOWANCE_GROSS_LOAN_PORTFOLIO (MIX, "ImpairmentLossAllowanceGrossLoanPortfolio", MONEY, INSTANT, BALANCE_SHEET),
	IMPAIRMENT_LOSS_REVERSAL_OF_IMPAIRMENT_LOSS_RECOGNISED_IN_PROFIT_OR_LOSS_GROSS_LOAN_PORTFOLIO (MIX, "ImpairmentLossReversalOfImpairmentLossRecognisedInProfitOrLossGrossLoanPortfolio", MONEY, INSTANT, BALANCE_SHEET),
	INCREASE_DECREASE_THROUGH_TRANSFERS_AND_OTHER_CHANGES_DONATED_EQUITY (MIX, "IncreaseDecreaseThroughTransfersAndOtherChangesDonatedEquity", MONEY, DURATION, BALANCE_SHEET),
	LOAN_PORTFOLIO (MIX, "LoanPortfolio", MONEY, INSTANT, BALANCE_SHEET),
	LOAN_PORTFOLIO_GROSS (MIX, "LoanPortfolioGross", MONEY, INSTANT, BALANCE_SHEET),
	OTHER_ACCOUNTS_RECEIVABLE (MIX, "OtherAccountsReceivable", MONEY, INSTANT, BALANCE_SHEET),
	PREPAYMENTS (MIX, "Prepayments", MONEY, INSTANT, BALANCE_SHEET),
	SUBORDINATED_DEBT (MIX, "SubordinatedDebt", MONEY, INSTANT, BALANCE_SHEET),
	TOTAL_CASH_AND_CASH_EQUIVALENTS (MIX, "TotalCashAndCashEquivalents", MONEY, INSTANT, BALANCE_SHEET),
	UNEARNED_INCOME_AND_DISCOUNT (MIX, "UnearnedIncomeAndDiscount", MONEY, INSTANT, BALANCE_SHEET),
	
	//Income Statmenets
	REVENUE_FROM_INTEREST (MIX, "RevenueFromInterest", MONEY, INSTANT, INCOME),
	FEE_AND_COMMISSION_INCOME_FROM_OTHER_FINANCIAL_SERVICES (MIX, "FeeAndCommissionIncomeFromOtherFinancialServices", MONEY, DURATION, INCOME),
	FEE_AND_COMMISSION_INCOME_ON_LOAN_PORTFOLIO (MIX, "FeeAndCommissionIncomeOnLoanPortfolio", MONEY, DURATION, INCOME),
	FEE_INCOME (MIX, "FeeIncome", MONEY, DURATION, INCOME),
	INTEREST_INCOME_FROM_INVESTMENTS (MIX, "InterestIncomeFromInvestments", MONEY, DURATION, INCOME),
	INTEREST_INCOME_ON_LOAN_PORTFOLIO (MIX, "InterestIncomeOnLoanPortfolio", MONEY, DURATION, INCOME),
	NON_OPERATING_INCOME (MIX, "NonOperatingIncome", MONEY, DURATION, INCOME),
	NON_OPERATING_REVENUE (MIX, "NonOperatingRevenue", MONEY, DURATION, INCOME),
	RECOVERIES_ON_LOANS_WRITTEN_OFF (MIX, "RecoveriesOnLoansWrittenOff", MONEY, DURATION, INCOME),
	
	//Expenses
	DEPRECIATION_AND_AMORTISATION_EXPENSE (IFRS, "DepreciationAndAmortisationExpense", MONEY, INSTANT, EXPENSE),
	ADMINISTRATIVE_EXPENSE (IFRS, "AdministrativeExpense", MONEY, INSTANT, EXPENSE),
	EMPLOYEE_BENEFITS_EXPENSE (IFRS, "EmployeeBenefitsExpense", MONEY, DURATION, EXPENSE),
	INTEREST_EXPENSE (IFRS, "InterestExpense", MONEY, DURATION, EXPENSE),
	DONATIONS (MIX, "Donations", MONEY, DURATION, EXPENSE),
	FEE_EXPENSE (MIX, "FeeExpense", MONEY, DURATION, EXPENSE),
	INTEREST_EXPENSE_ON_BORROWINGS (MIX, "InterestExpenseOnBorrowings", MONEY, DURATION, EXPENSE),
	INTEREST_EXPENSE_ON_DEPOSITS (MIX, "InterestExpenseOnDeposits", MONEY, DURATION, EXPENSE),
	MARKETING_EXPENSE (MIX, "MarketingExpense", MONEY, DURATION, EXPENSE),
	NON_OPERATING_EXPENSE (MIX, "NonOperatingExpense", MONEY, DURATION, EXPENSE),
	FEE_AND_COMMISSION_EXPENSE_ON_BORROWINGS (MIX, "FeeAndCommissionExpenseOnBorrowings", MONEY, DURATION, EXPENSE),
	FEE_AND_COMMISSION_EXPENSE_ON_DEPOSITS (MIX, "FeeAndCommissionExpenseOnDeposits", MONEY, DURATION, EXPENSE),
	OFFICE_SUPPLIES_EXPENSE (MIX, "OfficeSuppliesExpense", MONEY, DURATION, EXPENSE),
	OTHER_ADMINISTRATIVE_EXPENSE (MIX, "OtherAdministrativeExpense", MONEY, DURATION, EXPENSE),
	OTHER_FEE_AND_COMMISSION_EXPENSE (MIX, "OtherFeeAndCommissionExpense", MONEY, DURATION, EXPENSE),
	RENT_AND_UTILITIES_EXPENSE (MIX, "RentAndUtilitiesExpense", MONEY, DURATION, EXPENSE),
	TRANSPORTATION_EXPENSE (MIX, "TransportationExpense", MONEY, DURATION, EXPENSE),
	WRITE_OFFS_ON_GROSS_LOAN_PORTFOLIO (MIX, "WriteOffsOnGrossLoanPortfolio", MONEY, DURATION, EXPENSE),
	TRAINING_EXPENSE (MIX, "TrainingExpense", MONEY, DURATION, EXPENSE),
	INSURANCE_EXPENSE (MIX, "InsuranceExpense", MONEY, DURATION, EXPENSE),
	BOARD_OF_DIRECTORS_COMPENSATION (MIX, "BoardOfDirectorsCompensation", MONEY, DURATION, EXPENSE),
	AFFILIATION_AND_MEMBERSHIP_FEES (MIX, "AffiliationAndMembershipFees", MONEY, DURATION, EXPENSE),
	INTEREST_EXPENSE_ON_SUBORDINATED_DEBT (MIX, "InterestExpenseOnSubordinatedDebt", MONEY, DURATION, EXPENSE),
	
	//General
	NAME_OF_ENTITY(IFRS,"NameOfReportingEntityOrOtherMeansOfIdentification", STRING, null, GENERAL),
	LEGAL_ENTITY_FORM(IFRS, "LegalFormOfEntity", STRING, null, GENERAL),
	POINTS_OF_SERVICE(IFRS, "NumberOfPointsOfService", INTEGER, null, GENERAL),
	NUMBER_OF_EMPLOYEES(MIX, "NumberOfEmployees", INTEGER, null, GENERAL),
	NUMBER_OF_OFFICES(MIX, "NumberOfOffices", INTEGER, null, GENERAL),
	COUNTRY_OF_INCORPORATION(IFRS, "CountryOfIncorporation", STRING, null, GENERAL),
	DESCRIPTION_OF_NATURE_OF_ENTITYS_OPERATIONS_AND_PRINCIPAL_ACTIVITIES (IFRS, "DescriptionOfNatureOfEntitysOperationsAndPrincipalActivities", STRING, null, GENERAL),
	DESCRIPTION_OF_PRESENTATION_CURRENCY(IFRS, "DescriptionOfPresentationCurrency", STRING, null, GENERAL),
	STATEMENT_OF_IFRS_COMPLIANCE(IFRS, "StatementOfIFRSCompliance", STRING, null, GENERAL),
	DATE_ENTITY_ESTABLISHED (MIX, "DateEntityEstablished", STRING, null, GENERAL),
	DATE_OF_END_OF_REPORTING_PERIOD (MIX, "DateOfEndOfReportingPeriod", STRING, null, GENERAL),
	INSTITUTION_USES_INFLATION_ADJUSTMENTS (MIX, "InstitutionUsesInflationAdjustments", STRING, null, GENERAL),
	ADDRESS_OF_REGISTERED_OFFICE_OF_ENTITY (MIX, "AddressOfRegisteredOfficeOfEntity", STRING, null, GENERAL),
	DISCLOSURE_OF_ALLOWANCE_ACCOUNT_FOR_CREDIT_LOSSES_EXPLANATORY (IFRS, "DisclosureOfAllowanceAccountForCreditLossesExplanatory", STRING, null, GENERAL),
	DISCLOSURE_OF_SUMMARY_OF_SIGNIFICANT_ACCOUNTING_POLICIES_EXPLANATORY (IFRS, "DisclosureOfSummaryOfSignificantAccountingPoliciesExplanatory", STRING, null, GENERAL),
	OTHER_EXPLANATORY_DISCLOSURE_EXPLANATORY (IFRS, "OtherExplanatoryDisclosureExplanatory", STRING, null, GENERAL),

	//Indicators
	NUMBER_OF_ACTIVE_CLIENTS_ALL_SERVICES (MIX, "NumberOfActiveClientsAllServices", INTEGER, INSTANT, null),
	NUMBER_OF_ACTIVE_BORROWERS (MIX, "NumberOfActiveBorrowers", INTEGER, INSTANT, null),
	NUMBER_OF_DEPOSITORS(MIX, "NumberOfDepositors", INTEGER, INSTANT, null),
	NUMBER_OF_LOAN_OFFICERS(MIX,"NumberOfLoanOfficers", INTEGER, INSTANT, null),
	NUMBER_OF_DEPOSIT_ACCOUNTS (MIX, "NumberOfDepositAccounts", INTEGER, INSTANT, null),
	NUMBER_OF_OUTSTANDING_LOANS(MIX, "NumberOfOutstandingLoans", INTEGER, INSTANT, null),

	;

	private ElementCategory category;
	private PeriodType period;
	private ElementType type;
	private String name;
	private String description;
	private Namespace namespace;


	private XBRLElement(Namespace namespace, String name, ElementType type, PeriodType period, ElementCategory category) {
		this.namespace = namespace;
		this.name = name;
		this.type = type;
		this.period = period;
		this.category = category;
	}

	public ElementType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Namespace getNamespace() {
		return namespace;
	}
	
	public String getFullName() {;
		return namespace.getPrefix() + ":" + name;
	}

	public ElementCategory getCategory() {
		return category;
	}

	public PeriodType getPeriod() {
		return period;
	}

}
