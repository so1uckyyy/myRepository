package com.chixing.service;

import com.chixing.entity.Note;

import java.util.List;

public interface NoteService {
    /**
     * 首页中展示最新的10条游记
     * @return 游记集合对象
     */
    public List<Note> getLastTen();

    /**
     * 添加新游记
     * @param note 新游记对象
     * @return  返回是否添加成功
     */
    public boolean save(Note note);

    /**
     * 获得我的所有游记
     * @param custId 当前用户id
     * @return  游记集合对象
     */
    public List<Note> getMyList(int custId);

    /**
     * 获得我最新写的游记
     * @param custId 当前用户id
     * @return 游记对象
     */
    public Note getMyLastNote(int custId);
    /**
     * 根据游记Id查询游记
     * @param noteId 当前游记id
     * @return 游记对象
     */
    public Note getById(int noteId);
    /**
     * 分页查询游记
     * @param pageNum 页码
     * @return  返回当前页面的游记信息
     */
    public List<Note> getAll(int pageNum);
    /**
     * 获得游记总记录数
     * @return 返回游记总记录数
     */
    public int getCount();
}
