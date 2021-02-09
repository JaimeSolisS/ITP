import React from 'react';

import Axios from '../api'; 

export default class PersonList extends React.Component {
  state = {
    persons: []
  }

  componentDidMount() {
    Axios.get('users')
      .then(res => {
        console.log(res.data);
        this.setState({ persons: res.data });
      })
  }

  render() {
    return (
      <ul>
        { this.state.persons.map(person => <li key={person.id}>{person.firstName}</li>)}
      </ul>
    )
  }
}