package com.example.todolist
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import kotlinx.andriod.main.item_todo.*

class TodoAdapter (
    private val todos:MutableList<Todo>
    ) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        class TodoViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false

            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteDoneTodos(){
        todos.removeAll{ todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean ){
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG

        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            val tv: TextView = this.findViewById(R.id.tvTodoTitle)
            val cb: CheckBox = this.findViewById(R.id.cbDone)
            tv.text = curTodo.title
            cb.isChecked = curTodo.isChecked
            toggleStrikeThrough(tv, curTodo.isChecked)
            cb.setOnCheckedChangeListener{ _, isChecked ->
                toggleStrikeThrough(tv, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }

        }

    }

    override fun getItemCount(): Int {
        return todos.size
    }
}