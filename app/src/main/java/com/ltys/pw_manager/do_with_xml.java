package com.ltys.pw_manager;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;

public class do_with_xml{
	private DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
    private Element node=null,node_note=null,root=null,del_node=null;
    private Document xmldoc;
	private DocumentBuilder db;
	private String filename;
	/**do_with_xml 构造方法
	 * @param filename xml文件的名字
	 * 
	 */
	public do_with_xml(String filename){
		try{
			this.filename = filename;
			this.factory.setIgnoringElementContentWhitespace(true);
	    	this.db = factory.newDocumentBuilder();
	    	this.xmldoc = db.parse(new File(filename));
	    	this.root = xmldoc.getDocumentElement();
	    }catch (Exception e){
            e.printStackTrace();
        }
	}

	/**
	 * add_node 添加节点
	 * @param name 节点的名字属性，唯一
	 * @param note 节点的备注
	 * @return  添加成功返回0，已存在同名节点返回1
	 */
	public int add_node(String name,String note){
		if(exist(name)){
			return 1;
		}
		try{
            node = xmldoc.createElement("image");
            node.setAttribute("name",name);
            node_note = xmldoc.createElement("note");
            node_note.setTextContent(note);
            node.appendChild(node_note);
            root.appendChild(node);
            save();
		} catch (Exception e){
            e.printStackTrace();
        }
        return 0;
	}

	/**
	 * del_node 根据名字属性删除节点
	 * @param name 要删除的节点名
	 * @return 成功删除返回0，没有找到节点返回1
	 */
	public int del_node_by_name(String name){
		if(exist(name)){
			del_node = (Element)selectSingleNode("/root/image[@name='"+name+"']",root);
			del_node.getParentNode().removeChild(del_node);
			save();
		}
		else{
			return 1;
		}
		return 0;
	}

	/**
	 * save 仅限内部调用的保存xml文件的方法
	 */
	private void save(){
		try{
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty("encoding","utf-8");
			Result result = new StreamResult(new File(filename));
			Source xmlSource = new DOMSource(xmldoc);
			transformer.transform(xmlSource, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * selectSingleNode 查找第一个符合条件的节点
	 * @param  express 字符串查找式
	 * @param  source  要查找节点的父节点
	 * @return 返回第一个符合条件的Node类型的节点，可直接转换成Element类型使用
	 */
	public Node selectSingleNode(String express, Object source) {// 查找节点，并返回第一个符合条件节点
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try{
			result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
		}catch(XPathExpressionException e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * modify_node 修改节点
	 * @param  old_name 旧的节点名字属性值
	 * @param  new_name 新的属性值
	 * @param  new_note 新的描述值
	 * @return          返回值为1表示根据old_name未找到节点,返回值为2表示已存在name值为new_name的节点,正常修改返回0
	 */
	public int modify_node(String old_name,String new_name,String new_note){
		node = null;
		node = (Element)selectSingleNode("/root/image[@name='"+old_name+"']",root);
		if(node==null){
			return 1;
		}
		if(exist(new_name)&&!new_name.equals(old_name)){
			return 2;
		}
		node_note = (Element)node.getElementsByTagName("note").item(0);
		if(node_note==null){
			node_note = xmldoc.createElement("note");
			node.appendChild(node_note);
		}
		if(new_note!=null){
			node_note.setTextContent(new_note);
		}
		node.setAttribute("name",new_name);
		save();
		return 0;
	}

	/**
	 * exist 根据name属性值检查节点是否存在
	 * @param  name 要查找的name属性值
	 * @return      存在返回true，否则返回false
	 */
	public Boolean exist(String name){
		if(selectSingleNode("/root/image[@name='"+name+"']",root)!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * [get_note_by_name description]
	 * @param  name 要获取的节点的名字属性
	 * @return      如果找到指定节点的note值,则返回note值,否则返回null
	 */
	public String get_note_by_name(String name){
		if(exist(name)){
			node = (Element)selectSingleNode("/root/image[@name='"+name+"']",root);
			node_note = (Element)node.getElementsByTagName("note").item(0);
			if(node_note==null){
				return null;
			}
			else{
				return node_note.getTextContent();
			}
		}
		else{
			return null;
		}
	}
}