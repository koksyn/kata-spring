import React from "react";

const Tasks = ({tasks}) => {
    return (
        <div>
            <h1>Tasks list</h1>
            {
                tasks.map((task) => (
                    <div key={task.id} className="card">
                        <div className="card-body">
                            <h5 className="card-title">{task.title}</h5>
                            <h6 className="card-subtitle mb-2 text-muted">{task.description}</h6>
                            <h6 className="card-subtitle mb-2 text-muted">{task.author}</h6>
                        </div>
                    </div>
                ))
            }
        </div>
    )
};

export default Tasks