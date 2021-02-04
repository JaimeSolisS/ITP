import React from 'react';

import Axios from '../api';

export default class PersonInput extends React.Component {
  state = {
    id: 0, 
  }

  handleChange = event => {
      this.setState({id: event.target.value});
  };

  handleSubmit = event => {
      event.preventDefault();

      Axios.delete(`users/${this.state.id}`)
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