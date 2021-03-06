package com.epam.rates;

import com.epam.rates.exception.WrongDataException;
import com.epam.rates.model.Tariffs;
import com.epam.rates.parser.TariffsParser;
import com.epam.rates.parser.factory.TariffsParserFactory;
import com.epam.rates.validation.XmlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlDirector {
    private static final Logger LOGGER = LogManager.getLogger(XmlDirector.class);
    private final TariffsParserFactory factory;
    private final XmlValidator validator;

    public XmlDirector(TariffsParserFactory factory, XmlValidator validator) {
        this.factory = factory;
        this.validator = validator;
    }

    public Tariffs process(String source) {
        Tariffs tariffs = new Tariffs();
        try {
            TariffsParser parser = factory.get();
            if (validator.validate(source)) {
                tariffs = parser.parse(source);
            }
        } catch (WrongDataException e) {
            LOGGER.error("Invalid file supplied", e);
        }
        return tariffs;
    }

}
