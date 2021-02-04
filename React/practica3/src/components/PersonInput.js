import React from 'react';

import Axios from '../api';

export default class PersonInput extends React.Component {
  state = {
    name: '', 
  }

  handleChange = event => {
      this.setState({name: event.target.value});
  };

  handleSubmit = event => {
      event.preventDefault();

      const user = {
          name: this.state.name
      }

      Axios.post('users', {user })
      .then(res => {
          console.log(res);
          console.log(res.data );
      } ) 

  };


  render() {
    return (
      <form onSubmit={this.handleSubmit}>
          <label>
              Person Name:
              <input type="text" name="name" onChange={this.handleChange}/>
          </label>
          <button type="submit">Add</button>
      </form>
    )
  }
}