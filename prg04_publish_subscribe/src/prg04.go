/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg04 - Publish Subscribe Simulation
 * Student Name: Austin Gailey
 */

package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Pubsub struct {
	mu     sync.RWMutex
	subs   map[string][]chan string
	closed bool
}

func NewPubsub() *Pubsub {
	ps := &Pubsub{}
	ps.subs = make(map[string][]chan string)
	return ps
}

var wg sync.WaitGroup

// TODOd: creates and returns a new channel on a given topic, updating the Pubsub struct
func (ps *Pubsub) Subscribe(topic string) <-chan string {
	ps.mu.Lock()
	defer ps.mu.Unlock()

	ch := make(chan string, 1)
	ps.subs[topic] = append(ps.subs[topic], ch)
	return ch
}

// TODOd: writes the given message on all the channels associated with the given topic
func (ps *Pubsub) Publish(topic string, msg string) {
	ps.mu.RLock()
	defer ps.mu.RUnlock()
	if ps.closed {
		return
	}
	for _, ch := range ps.subs[topic] {
		go func(ch chan string) {
			ch <- msg
		}(ch)
	}
}

func (ps *Pubsub) Close() {
	ps.mu.Lock()
	if !ps.closed {
		ps.closed = true
		for _, subs := range ps.subs {
			for _, ch := range subs {
				close(ch)
			}
		}
	}
	ps.mu.Unlock()
}

// TODOd sends messages taken from a given array of message, one at a time and at random intervals, to all topic subscribers
func publisher(ps *Pubsub, topic string, msgs []string) {
	for _, msg := range msgs {
		time.Sleep(time.Duration(rand.Intn(3)) * time.Second)
		//fmt.Println(msg)
		ps.Publish(topic, msg)
	}
	wg.Done()
}

// TODO: reads and displays all messages received from a particular topic
func subscriber(ps *Pubsub, name string, topic string) {
	ps.mu.RLock()
	for {
		if msg, ok := <-ps.subs[topic][0]; ok {
			//msg := <-ps.subs[topic][0]
			fmt.Println(name + " Received: " + msg)
		} else {
			break
		}
	}
	ps.mu.RUnlock()
	wg.Done()
}

func main() {

	// TODOd: create the ps struct
	ps := NewPubsub()
	ps.Subscribe("cars")
	ps.Subscribe("games")

	// TODOd: create the arrays of messages to be sent on each topic
	carMessages := []string{
		"car 1 fact",
		"car 2 fact",
		"car 3 fact"}

	gameMessages := []string{
		"game 1 fact",
		"game 2 fact",
		"game 3 fact"}

	// TODOd: set wait group to 2 (# of publishers)
	wg.Add(2)
	// TODOd: create the publisher goroutines
	go publisher(ps, "cars", carMessages)
	go publisher(ps, "games", gameMessages)

	// TODO: create the subscriber goroutines
	go subscriber(ps, "John", "games")
	go subscriber(ps, "Mary", "cars")
	go subscriber(ps, "Mary", "games")
	// TODO: wait for all publishers to be done
	wg.Wait()
}
