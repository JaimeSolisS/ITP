import React from 'react';

import axios from 'axios';

export default class PersonInput extends React.Component {
  state = {
    id: 0, 
  }

  handleChange = event => {
      this.setState({id: event.target.value});
  };

  handleSubmit = event => {
      event.preventDefault();

      axios.delete(`https://jsonplaceholder.typicode.com/users/${this.state.id}`)
      .then(res => {
          console.log(res);
          console.log(res.data );
      } ) 

  };


  render() {
    return (
      <form onSubmit={this.handleSubmit}>
          <label>
              Person ID:
              <input type="number" name="name" onChange={this.handleChange}/>
          </label>
          <button type="submit">Delete</button>
      </form>
    )
  }
}