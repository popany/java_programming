// https://refactoring.guru/design-patterns/template-method

package org.example.template_method_pattern;

import org.example.template_method_pattern.AngryOperator;

public class TemplateMethodPatternDemo {
    public static void main(String[] args) {
        AngryOperator angryOperator = new AngryOperator();
        angryOperator.startOperation();
    }
}
