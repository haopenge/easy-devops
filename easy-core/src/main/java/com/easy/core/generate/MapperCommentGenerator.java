package com.easy.core.generate;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * @author liuph
 */
public class MapperCommentGenerator implements CommentGenerator {

    public MapperCommentGenerator() {
        super();
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        return;
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        String sb = "  WARNING - " +
                MergeConstants.NEW_ELEMENT_TAG;
        xmlElement.addElement(new TextElement(sb));
        xmlElement.addElement(new TextElement("-->"));
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        return;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
    }

    /**
     * Example使用
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    /**
     * 给字段添加数据库备注
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            String sb = " * " +
                    introspectedColumn.getRemarks();
            field.addJavaDocLine(sb);
            field.addJavaDocLine(" */");
        }

        //添加注解
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }
        String column = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column) || introspectedColumn.isColumnNameDelimited()) {
            column = introspectedColumn.getContext().getBeginningDelimiter()
                    + column
                    + introspectedColumn.getContext().getEndingDelimiter();
        }
        if (!column.equals(introspectedColumn.getJavaProperty())) {
            field.addAnnotation("@Column(name = \"" + column + "\")");
            String remarks = introspectedColumn.getRemarks();
            if(StringUtils.isNotBlank(remarks)){
                field.addAnnotation("@ApiModelProperty(\"" + remarks + "\")");
            }

        }
        if (introspectedColumn.isIdentity()) {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        } else if (introspectedColumn.isSequenceColumn()) {
            field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
        }
    }

    /**
     * Example使用
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    /**
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getter方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    }

    /**
     * setter方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    /**
     * Example使用
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }
}
