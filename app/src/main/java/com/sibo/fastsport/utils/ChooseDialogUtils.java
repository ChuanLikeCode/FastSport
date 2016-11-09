package com.sibo.fastsport.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.sibo.fastsport.adapter.MyChooseActionAdapter;
import com.sibo.fastsport.domain.SportName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/09.
 */
public class ChooseDialogUtils {

    private String[] part_tpye = {"胸部", "背部", "腿部", "肱二头肌", "肱三头肌", "肩部", "前臂", "腹部"};
    private String[] equipment_type = {"哑铃", "健身房器械", "无器械", "杠铃"};
    private String[] action_type = {"热身动作", "拉伸", "具体动作", "放松动作"};
    private String[] level_type = {"零基础", "初学", "进阶", "强化", "挑战"};
    private MyChooseActionAdapter adapter;
    private Context context;
    private List<SportName> list = new ArrayList<>();
    private AlertDialog.Builder builder;
    private int select;
    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            list.clear();
            Log.e("select", select + "---which=" + which);
            show(select, which);
        }
    };

    public ChooseDialogUtils(Context context, List<SportName> list, MyChooseActionAdapter adapter) {
        this.adapter = adapter;
        this.context = context;
        this.list = list;
        builder = new AlertDialog.Builder(context);
    }

    public void showDialog(int select) {
        setSelect(select);
        switch (select) {
            case 0:
                builder.setItems(part_tpye, listener);
                break;
            case 1:
                builder.setItems(equipment_type, listener);
                break;
            case 2:
                builder.setItems(action_type, listener);
                break;
            case 3:
                builder.setItems(level_type, listener);
                break;
        }
        builder.show();
    }

    private void setSelect(int select) {
        this.select = select;
    }

    public void show(int type, int which) {
        switch (type) {
            case 0:
                showPart(which);
                break;
            case 1:
                showEquipment(which);
                break;
            case 2:
                showActionType(which);
                break;
            case 3:
                showLevel(which);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private void showLevel(int which) {
        switch (which) {
            case 0:
                list.addAll(MyBombUtils.list_level_1);
                Log.e("list-showLevel", list.size() + "");
                break;
            case 1:
                list.addAll(MyBombUtils.list_level_2);
                Log.e("list-showLevel", list.size() + "");
                break;
            case 2:
                list.addAll(MyBombUtils.list_level_3);
                Log.e("list-showLevel", list.size() + "");
                break;
            case 3:
                list.addAll(MyBombUtils.list_level_4);
                Log.e("list-showLevel", list.size() + "");
                break;
            case 4:
                list.addAll(MyBombUtils.list_level_5);
                Log.e("list-showLevel", list.size() + "");

                break;
        }
    }

    private void showActionType(int which) {
        switch (which) {
            case 0:
                list.addAll(MyBombUtils.list_type_reshen);
                Log.e("list-showActionType", list.size() + "");
                break;
            case 1:
                list.addAll(MyBombUtils.list_type_lashen);
                Log.e("list-showActionType", list.size() + "");
                break;
            case 2:
                list.addAll(MyBombUtils.list_type_juti);
                Log.e("list-showActionType", list.size() + "");
                break;
            case 3:
                list.addAll(MyBombUtils.list_type_fangsong);
                Log.e("list-showActionType", list.size() + "");
                break;
        }
    }

    private void showEquipment(int which) {
        switch (which) {
            case 0:
                list.addAll(MyBombUtils.list_equipment_yaling);
                Log.e("list-showEquipment", list.size() + "");
                break;
            case 1:
                list.addAll(MyBombUtils.list_equipment_jianshenfang);
                Log.e("list-showEquipment", list.size() + "");
                break;
            case 2:
                list.addAll(MyBombUtils.list_equipment_wuqixie);
                Log.e("list-showEquipment", list.size() + "");
                break;
            case 3:
                list.addAll(MyBombUtils.list_equipment_gangling);
                Log.e("list-showEquipment", list.size() + "");
                break;
        }
    }

    private void showPart(int which) {
        switch (which) {
            case 0:
                list.addAll(MyBombUtils.list_part_chest);
                break;
            case 1:
                list.addAll(MyBombUtils.list_part_beibu);
                break;
            case 2:
                list.addAll(MyBombUtils.list_part_tuibu);
                break;
            case 3:
                list.addAll(MyBombUtils.list_part_gongerji);
                break;
            case 4:
                list.addAll(MyBombUtils.list_part_gongsanji);
                break;
            case 5:
                list.addAll(MyBombUtils.list_part_jianbu);
                break;
            case 6:
                list.addAll(MyBombUtils.list_part_qianbi);
                break;
            case 7:
                list.addAll(MyBombUtils.list_part_fubu);
                break;
        }
    }
}
