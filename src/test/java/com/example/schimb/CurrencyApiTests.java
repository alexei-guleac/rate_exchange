package com.example.schimb;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = CurrencyApiTests.SimpleConfiguration.class)
@Slf4j
public class CurrencyApiTests implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private boolean beanInitialized = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.beanInitialized = true;
    }

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void givenCurrencyCode_whenString_thanExist() {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        assertNotNull(usd);
        assertEquals(usd.getCurrencyCode(), "USD");
        assertEquals(usd.getNumericCode(), 840);
        assertEquals(usd.getDefaultFractionDigits(), 2);
    }

    @Test
    public void givenCurrencyCode_whenNoExist_thanThrowsError() {
        assertThrows(Exception.class,
                () -> {
                    Monetary.getCurrency("AAA");
                });
    }

    @Test
    public void givenAmounts_whenStringified_thanEquals() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory()
                .setCurrency(usd).setNumber(200).create();
        Money moneyof = Money.of(12, usd);
        FastMoney fastmoneyof = FastMoney.of(2, usd);

        assertEquals("USD", usd.toString());
        assertEquals("USD 200.00", fstAmtUSD.toString());
        assertEquals("USD 12.00", moneyof.toString());
        assertEquals("USD 2.00", fastmoneyof.toString());
    }

    @Test
    public void givenCurrencies_whenCompared_thanNotequal() {
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory()
                .setCurrency("USD").setNumber(1).create();
        Money oneEuro = Money.of(1, "EUR");

        assertFalse(oneEuro.equals(FastMoney.of(1, "EUR")));
        assertTrue(oneDolar.equals(Money.of(1, "USD")));
    }

    @Test
    public void givenAmount_whenDivided_thanThrowsException() {
        // assertThrows(NullPointerException.class,
        //         () -> {
        //             MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory()
        //                     .setCurrency("USD").setNumber(1).create();
        //             oneDolar.divide(3);
        //         });
    }

    @Test
    public void givenAmounts_whenSummed_thanCorrect() {
        MonetaryAmount[] monetaryAmounts = new MonetaryAmount[]{
                Money.of(100, "CHF"), Money.of(10.20, "CHF"), Money.of(1.15, "CHF")};

        Money sumAmtCHF = Money.of(0, "CHF");
        for (MonetaryAmount monetaryAmount : monetaryAmounts) {
            sumAmtCHF = sumAmtCHF.add(monetaryAmount);
        }

        assertEquals("CHF 111.35", sumAmtCHF.toString());
    }

    @Test
    public void givenArithmetic_whenStringified_thanEqualsAmount() {
        CurrencyUnit usd = Monetary.getCurrency("USD");

        Money moneyof = Money.of(12, usd);
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory()
                .setCurrency(usd).setNumber(200.50).create();
        MonetaryAmount oneDolar = Monetary.getDefaultAmountFactory()
                .setCurrency("USD").setNumber(1).create();
        Money subtractedAmount = Money.of(1, "USD").subtract(fstAmtUSD);
        MonetaryAmount multiplyAmount = oneDolar.multiply(0.25);
        MonetaryAmount divideAmount = oneDolar.divide(0.25);

        assertEquals("USD", usd.toString());
        assertEquals("USD 1.00", oneDolar.toString());
        assertEquals("USD 200.50", fstAmtUSD.toString());
        assertEquals("USD 12.00", moneyof.toString());
        assertEquals("USD -199.50", subtractedAmount.toString());
        assertEquals("USD 0.25", multiplyAmount.toString());
        assertEquals("USD 4.00", divideAmount.toString());
    }

    @Test
    public void givenAmount_whenRounded_thanEquals() {
        MonetaryAmount fstAmtEUR = Monetary.getDefaultAmountFactory()
                .setCurrency("EUR").setNumber(1.30473908).create();
        MonetaryAmount roundEUR = fstAmtEUR.with(Monetary.getDefaultRounding());

        // assertEquals("EUR 1.30473908", fstAmtEUR.toString());
        assertEquals("EUR 1.30", roundEUR.toString());
    }

    @Test
    public void givenAmount_whenConversion_thenNotNull() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD")
                .setNumber(1).create();

        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");

        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);

        assertEquals("USD 1.00", oneDollar.toString());
        assertNotNull(convertedAmountUSDtoEUR);
        log.info(convertedAmountUSDtoEUR.toString());
    }

    @Test
    public void getAllJavaUtilCurrencies() {
        ArrayList<String> currencies = new ArrayList<>();
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale loc : locales) {
            try {
                String val = Currency.getInstance(loc).getCurrencyCode();
                if (!currencies.contains(val))
                    currencies.add(val);
            } catch (Exception exc) {
                // Locale not found
            }
            Collections.sort(currencies);
        }
        log.info(currencies.toString());
        log.info(String.valueOf(currencies.size()));
    }

    @Test
    public void getAllJavaMonetaCurrencies() {
        ArrayList<CurrencyUnit> currencies = new ArrayList<>();
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale loc : locales) {
            try {
                CurrencyUnit unit = Monetary.getCurrency(loc);
                if (!currencies.contains(unit))
                    currencies.add(unit);
            } catch (Exception exc) {
                // Locale not found
            }
            Collections.sort(currencies);
        }
        log.info(currencies.toString());
        log.info(String.valueOf(currencies.size()));
    }

    @Test
    public void givenLocale_whenFormatted_thanEquals() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory()
                .setCurrency("USD").setNumber(1).create();

        MonetaryAmountFormat formatUSD = MonetaryFormats.getAmountFormat(Locale.US);
        String usFormatted = formatUSD.format(oneDollar);

        assertEquals("USD 1.00", oneDollar.toString());
        assertNotNull(formatUSD);
        assertEquals("USD1.00", usFormatted);
    }

    @Test
    public void givenAmount_whenCustomFormat_thanEquals() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory()
                .setCurrency("USD").setNumber(1).create();

        MonetaryAmountFormat customFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.
                of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "00000.00 ¤").build());
        String customFormatted = customFormat.format(oneDollar);

        assertNotNull(customFormat);
        assertEquals("USD 1.00", oneDollar.toString());
        assertEquals("00001.00 US Dollar", customFormatted);
    }

    @Configuration
    public static class SimpleConfiguration {
    }
}
