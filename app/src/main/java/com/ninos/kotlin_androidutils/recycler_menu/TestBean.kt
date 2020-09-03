package com.ninos.kotlin_androidutils.recycler_menu

object TestBean {
    fun getListDataForHome(): List<Any> {
        var list = ArrayList<Any>(4)
        list.add(Any())
        list.add(Any())
        list.add(Any())
        list.add(Any())
        return list
    }

    fun getListDataForClassification(): List<ClassificationInfo> {
        var list = ArrayList<ClassificationInfo>()
        list.add(ClassificationInfo(true, "New"))
        list.add(ClassificationInfo(false, "Info"))
        list.add(ClassificationInfo(false, "Classification"))
        list.add(ClassificationInfo(false, "Test"))
        list.add(ClassificationInfo(false, "Ajenda"))
        list.add(ClassificationInfo(false, "Wechat"))
        list.add(ClassificationInfo(false, "School"))
        list.add(ClassificationInfo(false, "Context"))
        list.add(ClassificationInfo(false, "Group"))
        list.add(ClassificationInfo(false, "Program"))
        return list
    }

    class ClassificationInfo(checked: Boolean, name: String) {
        public var checked = checked
        public var name = name
    }
}